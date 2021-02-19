package com.example.demo.response;

import com.example.demo.util.MessageStatus;
import com.example.demo.model.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {
    private String requestId;
    private String comments;

    private String id;

    private String phoneNumber;


    private String message;


    private MessageStatus status;


    private String failureCode;

    private String failureComments;


    private Date createdAt;

    private Date uploadedAt;


    public SuccessResponse(Message message) {
        this.id = message.getId();
        this.phoneNumber = message.getPhoneNumber();
        this.message = message.getMessage();
        this.status = message.getStatus();
        this.failureCode = message.getFailureComments();
        this.createdAt = message.getCreatedAt();
        this.uploadedAt = message.getUploadedAt();
        this.failureComments = message.getFailureComments();
    }


}
