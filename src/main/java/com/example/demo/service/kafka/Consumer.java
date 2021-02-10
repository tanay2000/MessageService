package com.example.demo.service.kafka;

import com.example.demo.MessageStatus;

import com.example.demo.exception.DatabaseCrashException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.message.MessageService;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Consumer {
    @Autowired
    MessageService messageService;
    @Autowired
    RedisService redisService;
    @Autowired
    MessageRepository messageRepository;

    @KafkaListener (topics="${kafka.topic}")
    public void consumeMessageId(String messageId) throws NotFoundException{
        try{
            Optional<Message> messageObject = Optional.ofNullable(messageRepository.findById(messageId).orElse(null));
            if(messageObject.isPresent()) {
                if(redisService.checkIfExist(messageObject.get().getPhoneNumber())==null){
                    messageObject.get().setStatus(MessageStatus.FAILURE);
                    messageObject.get().setFailureComments("Number is in blacklisted");
                    messageObject.get().setFailureCode("404");
                    System.out.println("blacklisted");
                    messageRepository.save(messageObject.get());
                }
                else{

                    // Perfoem 3 party api function here
                    // set status oof message according to api

                    System.out.println("not blacklisted");
                }
                //System.out.println(redisService.checkIfExist(entity.get().getPhoneNumber()));
            }
            else{
                throw new NotFoundException("User not found");
            }
        }
        catch (InvalidRequestException ex) {

            throw new DatabaseCrashException("Kafka error");
        }

    }
}
