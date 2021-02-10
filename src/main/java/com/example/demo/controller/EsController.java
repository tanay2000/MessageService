package com.example.demo.controller;




import com.example.demo.model.EsInput;
import com.example.demo.service.elastic.EsService;
import com.example.demo.model.EsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/elastic")
public class EsController {


@Autowired
EsService esService;

    @PostMapping
    public EsModel sendSms(@RequestBody EsModel esModel){
        esService.save(esModel);
        return esModel;
    }
    @GetMapping("/date")
    public Page<EsModel> getAllBetweenDate(@RequestBody EsInput esInput){

        return esService.findByDate(esInput);



    }
    @GetMapping("/message/{messageText}")
    public Page<EsModel> getAllByText(@PathVariable String messageText){
        return esService.findByMessage(messageText);
    }
}

