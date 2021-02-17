package com.example.demo.service.kafka;

import com.example.demo.IMIconnectApi.model.Channels;
import com.example.demo.IMIconnectApi.model.Destination;
import com.example.demo.IMIconnectApi.model.ImiSmsRequest;
import com.example.demo.IMIconnectApi.model.Sms;
import com.example.demo.IMIconnectApi.response.ExternalApiResponse;
import com.example.demo.IMIconnectApi.service.SmsSender;
import com.example.demo.MessageStatus;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Consumer {
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
                messageObject.get().setFailureCode("200");
                System.out.println("blacklisted");
                messageRepository.save(messageObject.get());
            } else {
                // Perform 3 party api function here
                List<String> phoneNumber = new ArrayList<>();
                phoneNumber.add(messageObject.get().getPhoneNumber());
                Sms sms = Sms.builder().text(messageObject.get().getMessage()).build();
                Channels channels = Channels.builder().sms(sms).build();
                Destination destination = Destination.builder()
                        .msisdn(phoneNumber)
                        .coRelationId(messageObject.get().getId())
                        .build();

                List<Destination> destinationList = new ArrayList<>();
                destinationList.add(destination);
                ImiSmsRequest imiSmsRequest = ImiSmsRequest.builder()
                        .channels(channels).deliveryChannel("sms")
                        .destinations(destinationList)
                        .build();

                String response = smsSender.smsSend(imiSmsRequest);
                Gson gson = new Gson();
                ExternalApiResponse externalApiResponse = gson.fromJson(response, ExternalApiResponse.class);

                System.out.println(externalApiResponse.getCode());
                //check if 3rd party api is success or failure
                if (externalApiResponse.getCode().equals("1001")) {
                    messageObject.get().setStatus(MessageStatus.SUCCESS);
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
