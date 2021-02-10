package com.example.demo.service.message;

import com.example.demo.model.Message;
import com.example.demo.model.UserSmsInput;

import java.util.Optional;

public interface MessageService {

    public Message sendSms(UserSmsInput userSmsInput);
    public Optional<Message> getMessageById(String id);

}