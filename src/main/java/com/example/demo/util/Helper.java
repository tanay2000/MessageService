package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Helper {

    public long DateConverter(String stringDate)  {
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();

        return epoch;
    }
}


//    String url = "https://api.imiconnect.in/resources/v1/messaging";
//    String key = "93ceffda-5941-11ea-9da9-025282c394f2";
//    RestTemplate restTemplate = new RestTemplateBuilder()
//            .rootUri(url)
//            .defaultHeader("key", key)
//            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//            .build();
//
//    ExternalApiResponse response = restTemplate.postForObject(url, finalRequest, ExternalApiResponse.class);