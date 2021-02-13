package com.example.demo.IMIconnectApi.service;

import com.example.demo.IMIconnectApi.model.ImiSmsRequest;
import com.example.demo.IMIconnectApi.response.ExternalApiResponse;

public interface SmsSender {
    public String smsSend(ImiSmsRequest imiSmsRequest);
}
