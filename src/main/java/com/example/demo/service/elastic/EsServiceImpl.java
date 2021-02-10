package com.example.demo.service.elastic;



import com.example.demo.model.EsInput;
import com.example.demo.model.EsModel;
import com.example.demo.repository.EsRepository;


import com.example.demo.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service

public class EsServiceImpl implements EsService {

    @Autowired
    EsRepository esRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    Helper helper;

    @Override
    public void save(EsModel eSmodel) {
        esRepository.save(eSmodel);
    }

    @Override
    public Page<EsModel> findByMessage(String messageText) {

        return esRepository.findByMessage(messageText,PageRequest.of(0,2));
    }

    @Override
    public Page<EsModel> findByDate(EsInput esInput) {

        long startEpoch= helper.DateConverter(esInput.getStartDate());
        long endEpoch=helper.DateConverter(esInput.getEndDate());

        return esRepository.findAllByCreatedAtBetween(startEpoch,endEpoch,PageRequest.of(0,2));



    }

}
