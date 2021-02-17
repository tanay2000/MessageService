package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackListInput {
    @JsonProperty("phone_numbers")
    @NotEmpty
    private ArrayList<String> phoneNumbers;

}
