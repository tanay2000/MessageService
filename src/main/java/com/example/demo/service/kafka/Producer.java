package com.example.demo.service.kafka;

import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    @Autowired
    private KafkaTemplate<Message,String> kafkaTemplate;
    @Value("${kafka.topic}")
    String Topic;
    public void sendMessageId(Message message){
        kafkaTemplate.send(Topic, message.getId());
    }
}
