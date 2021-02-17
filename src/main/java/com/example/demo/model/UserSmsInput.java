package com.example.demo.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UserSmsInput {

    @NotEmpty(message = "phone number has to be present")
    private String phoneNumber;
    @NotEmpty
    private String message;

}
