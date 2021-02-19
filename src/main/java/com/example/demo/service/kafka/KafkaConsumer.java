package com.example.demo.service.kafka;

import com.example.demo.IMIconnectApi.model.ImiSmsRequest;
import com.example.demo.IMIconnectApi.response.ExternalApiResponse;
import com.example.demo.IMIconnectApi.service.SmsSender;
import com.example.demo.util.MessageStatus;
import com.example.demo.exception.DatabaseCrashException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.EsModel;
import com.example.demo.model.Message;
import com.example.demo.repository.EsRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.message.MessageService;
import com.example.demo.service.redis.RedisService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KafkaConsumer {
    @Autowired
    MessageService messageService;
    @Autowired
    RedisService redisService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    EsRepository esRepository;
    @Autowired
    SmsSender smsSender;

    @KafkaListener(topics = "${kafka.topic}")
    public void consumeMessageId(String messageId) throws NotFoundException {
        try {
            if(!messageRepository.findById(messageId).isPresent())
                throw new NotFoundException("Cannot find the message in DB");
            Optional<Message> messageObject = messageRepository.findById(messageId);

            if (redisService.checkIfExist(messageObject.get().getPhoneNumber())) {
                messageObject.get().setStatus(MessageStatus.FAILURE);
                messageObject.get().setFailureComments("Blacklisted Number");
                messageObject.get().setFailureCode("403");
                System.out.println("blacklisted");
                messageRepository.save(messageObject.get());
            } else {

                // Perform 3 party api function here
                ImiSmsRequest imiSmsRequest = new ImiSmsRequest(messageObject.get());
                ExternalApiResponse response = smsSender.smsSend(imiSmsRequest);

                //check if 3rd party api is success or failure
                if (response.getApiResponseData().get(0).getCode().equals("1001")) {
                    messageObject.get().setStatus(MessageStatus.SUCCESS);
                    messageRepository.save(messageObject.get());
                }
                else{
                    messageObject.get().setStatus(MessageStatus.FAILURE);
                    messageRepository.save(messageObject.get());
                }

                //save in elastic search
                esRepository.save(new EsModel(messageObject.get()));
                System.out.println("Not blacklisted");
            }
        } catch (InvalidRequestException ex) {
            throw new DatabaseCrashException("Kafka error");
        }
    }
}
