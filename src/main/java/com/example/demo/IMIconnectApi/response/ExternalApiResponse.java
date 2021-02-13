package com.example.demo.IMIconnectApi.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiResponse {
    private String code;
    @JsonProperty("transid")
    private String transId;
    private String description;
    @JsonProperty("correlationid")
    private String correlationId;

}
