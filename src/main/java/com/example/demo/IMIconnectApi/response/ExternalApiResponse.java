package com.example.demo.IMIconnectApi.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiResponse {

    @JsonProperty("response")
    private ArrayList<ApiResponseData> apiResponseData;

    @Data
    public static class ApiResponseData {

        @JsonProperty("transid")
        private String transId;

        private String code;
        private String description;

        @JsonProperty("correlationid")
        private String correlationId;
    }
}
