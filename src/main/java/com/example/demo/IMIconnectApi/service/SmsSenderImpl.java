package com.example.demo.IMIconnectApi.service;

import com.example.demo.IMIconnectApi.model.ImiSmsRequest;
import com.example.demo.IMIconnectApi.response.ExternalApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class SmsSenderImpl implements SmsSender{

    @Override
    public String smsSend(ImiSmsRequest imiSmsRequest) {
        String url = "https://api.imiconnect.in/resources/v1/messaging";
        String key = "93ceffda-5941-11ea-9da9-025282c394f2";
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(url)
                .defaultHeader("key", key)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
        try{
            String response = restTemplate.postForObject(url,imiSmsRequest,String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode jsonNode = rootNode.path("response");
            JsonNode transid = rootNode.at("/response/transid");

            if(transid.toString().length()>0){
                return mapper.treeToValue(jsonNode,ExternalApiResponse.class).toString();
            }else{
                return jsonNode.get(0).toString();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "null";
    }
}
