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
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
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
    ElasticsearchRepository elasticsearchRepository;

    @KafkaListener (topics="${kafka.topic}")
    public void consumeMessageId(String messageId) throws NotFoundException{
        try{
            Optional<Message> messageObject = Optional.ofNullable(messageRepository.findById(messageId).orElse(null));
            if(messageObject.get()==null)
                throw new NotFoundException("Cannot find the message in DB");
            if(redisService.checkIfExist(messageObject.get().getPhoneNumber())){
                messageObject.get().setStatus(MessageStatus.FAILURE);
                messageObject.get().setFailureComments("Blacklisted Number");
                messageObject.get().setFailureCode("400");
                System.out.println("blacklisted");
                messageRepository.save(messageObject.get());
            }
            else{
                // Perfoem 3 party api function here
                // set status oof message according to api
                //do elastic save here

                elasticsearchRepository.save(messageObject.get());
                System.out.println("Not blacklisted");
            }
        }
        catch (InvalidRequestException ex) {
            throw new DatabaseCrashException("Kafka error");
        }
    }
}
