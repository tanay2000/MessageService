package com.example.demo.IMIconnectApi.service;

import com.example.demo.IMIconnectApi.model.ImiSmsRequest;
import com.example.demo.IMIconnectApi.response.ExternalApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class SmsSenderImpl implements SmsSender{
    @Value("${imiConnect.key}")
    private String key;
    @Value("${imiConnect.url}")
    private String url;
    @Override
    public ExternalApiResponse smsSend(ImiSmsRequest imiSmsRequest) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(url)
                .defaultHeader("key", key)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
        try{
            ExternalApiResponse externalApiResponse = restTemplate.postForObject(url,imiSmsRequest,ExternalApiResponse.class);
            return externalApiResponse;

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

//    HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HeaderKeys.CLIENT_ID_KEY, properties.getId());
//                httpHeaders.set(HeaderKeys.CLIENT_SECRET_KEY, properties.getSecret());
//                httpHeaders.set(com.meesho.commons.enums.CommonConstants.COUNTRY_HEADER, walletDebitRequest.getCountry().getCountryCode());
//                HttpEntity<WalletDebitRequest> httpEntity = new HttpEntity<>(walletDebitRequest, httpHeaders);
//        try {
//        return restTemplate.postForObject(url, httpEntity, WalletDebitTransaction.class);
//        } catch (HttpClientErrorException e) {
//        log.warn("Got client error exception fetching cart state", e);
//        return makeFailedDebitTransaction();
//        }