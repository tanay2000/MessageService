package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
@Data
public class UserSmsInput {

    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String message;

}
