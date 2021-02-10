package com.example.demo.controller;

import com.example.demo.MessageStatus;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Message;
import com.example.demo.model.UserSmsInput;
import com.example.demo.response.data;
import com.example.demo.response.error;
import com.example.demo.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/sms")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Object> sendSms(@RequestBody UserSmsInput userSmsInput)
    {
        if(userSmsInput.getMessage().isEmpty() || userSmsInput.getMessage().trim().isEmpty()) {

            return new ResponseEntity<>(new error("INVALID_REQUEST","message is mandatory"), HttpStatus.BAD_REQUEST);
        }
        if(userSmsInput.getPhoneNumber().isEmpty() || userSmsInput.getPhoneNumber().trim().isEmpty())
            return new ResponseEntity<>(new error("INVALID_REQUEST","phone_number is mandatory"), HttpStatus.BAD_REQUEST);


        try {
            Message message = messageService.sendSms(userSmsInput);
//            message.setStatus(MessageStatus.SUCCESS);
            MessageStatus messageStatus = message.getStatus();

            if (messageStatus == MessageStatus.SUCCESS) {
                data DataReturn = new data();
                DataReturn.setComments("Successfully Sent");
                DataReturn.setRequestId(message.getId());

                return new ResponseEntity<>(DataReturn, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new error("INVALID_REQUEST", "Blacklisted Number"), HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @GetMapping("/{request_id}")
    public ResponseEntity<Object>  getMessageById(@PathVariable String request_id){
       try{

            if (!messageService.getMessageById(request_id).isPresent()) {
                return new ResponseEntity<>(new error("INVALID_REQUEST", "request_id not found"), HttpStatus.NOT_FOUND);
            }
            else
            {
                data data = new data(messageService.getMessageById(request_id).get());
                return new ResponseEntity<>(data,HttpStatus.OK);
            }


    }
        catch(Exception ex){
            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
//    return messageService.getMessageById(request_id);
    }

}

//  kafka, zookeeper server start
//  bin/zookeeper-server-start.sh config/zookeeper.properties;
//  bin/kafka-server-start.sh config/server.properties
//  ./bin/elasticsearch
//  src/redis-server












