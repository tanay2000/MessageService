package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(indexName = "sms")
public class EsModel {

    @Id
    private String id;
    private String phoneNumber;
    private String message;
    private Date createdAt = new Date();


    public EsModel(Message message) {

        this.id = message.getId();
        this.phoneNumber = message.getPhoneNumber();
        this.message = message.getMessage();
        this.createdAt = message.getUploadedAt();

    }

}