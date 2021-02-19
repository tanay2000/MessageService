package com.example.demo.IMIconnectApi.model;

import com.example.demo.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImiSmsRequest {
    @JsonProperty("deliverychannel")
    private String deliveryChannel;
    private Channels channels;
    @JsonProperty("destination")
    private List<Destination> destinations;
    public ImiSmsRequest(Message message){
        this.deliveryChannel="sms";
        this.channels = new Channels(new Sms(message.getMessage()));
        Destination dest = Destination.builder().coRelationId(message.getId()).msisdn(Arrays.asList(message.getPhoneNumber())).build();
        this.destinations = Arrays.asList(dest);
    }
}
