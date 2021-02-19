package com.example.demo.service.message;

import com.example.demo.util.MessageStatus;
import com.example.demo.model.Message;
import com.example.demo.model.UserSmsInput;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.kafka.KafkaProducer;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    RedisService redisService;



    @Override
    public Message sendSms(UserSmsInput userSmsInput) {
        Message message=new Message();
        message.setMessage(userSmsInput.getMessage());
        message.setPhoneNumber((userSmsInput.getPhoneNumber()));
        message.setId(UUID.randomUUID().toString());
        message.setStatus(MessageStatus.QUEUED);
        messageRepository.save(message);

        kafkaProducer.sendMessageId(message);

        return message;
    }

    @Override
    public Optional<Message> getMessageById(String id) {


        return messageRepository.findById(id);


    }
}