package com.example.demo.model;

import com.example.demo.MessageStatus;

import javax.persistence.*;
import java.util.Date;

@Entity

@Table(name="sms_requests")
public class Message {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private String id;
    
    @Column(name="phone_number",nullable = false)
    private String phoneNumber;

    @Column(name="message",nullable = false)
    private String message;

    @Column(name="status",nullable = false)
    private MessageStatus status;

    @Column(name="failure_code")
    private  String failureCode;

    @Column(name="failure_comments")
    private String failureComments;

    @Column(name="create_at",nullable = false)
    private Date createdAt=new Date();

    @Column(name="uploadedAt",nullable = false)
    private Date uploadedAt=new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureComments() {
        return failureComments;
    }

    public void setFailureComments(String failureComments) {
        this.failureComments = failureComments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", failureCode='" + failureCode + '\'' +
                ", failureComments='" + failureComments + '\'' +
                ", createdAt=" + createdAt +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}