package com.example.demo.service.kafka;

import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class Producer {
    @Autowired
    private KafkaTemplate<Message,String> kafkaTemplate;
    @Value("${kafka.topic}")
    String Topic;
    public void sendMessageId(Message message){

        try {
            kafkaTemplate.send(Topic, message.getId());
        }
        catch(Exception e){
            System.out.println(e);
        }
//        ListenableFuture<SendResult<Message, String>> future =
//                kafkaTemplate.send(Topic, message.getId());
//
//        future.addCallback(new ListenableFutureCallback<SendResult<Message, String>>() {
//
//            @Override
//            public void onSuccess(SendResult<Message, String> result) {
//                System.out.println("Sent message=[" + message.getId() +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message=["
//                        + message.getId() + "] due to : " + ex.getMessage());
//            }
//        });
    }
}

