package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.model.UserSmsInput;
import com.example.demo.response.SuccessResponse;
import com.example.demo.response.error;
import com.example.demo.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/v1/sms")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Object> sendSms(@Valid @RequestBody UserSmsInput userSmsInput) {

        try {
            Message message = messageService.sendSms(userSmsInput);
            SuccessResponse successResponse = new SuccessResponse();
            successResponse.setComments("Successfully Sent");
            successResponse.setRequestId(message.getId());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{request_id}")
    public ResponseEntity<Object> getMessageById(@PathVariable String requestId) {
        try {

            if (!messageService.getMessageById(requestId).isPresent()) {
                return new ResponseEntity<>(new error("INVALID_REQUEST", "request_id not found"), HttpStatus.NOT_FOUND);
            } else {
                SuccessResponse successResponse = new SuccessResponse(messageService.getMessageById(requestId).get());
                return new ResponseEntity<>(successResponse, HttpStatus.OK);
            }


        } catch (Exception ex) {
            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}

//  kafka, zookeeper server start
//  bin/zookeeper-server-start.sh config/zookeeper.properties;
//  bin/kafka-server-start.sh config/server.properties
//  ./bin/elasticsearch
//  src/redis-server












