package com.example.demo.service.elastic;



import com.example.demo.model.EsInput;
import com.example.demo.model.EsModel;
import org.springframework.data.domain.Page;

public interface EsService {

    public void save(EsModel esModel);
    public Page<EsModel> findByMessage(String messageText);
    public Page<EsModel> findByDate(EsInput esInput);


}
