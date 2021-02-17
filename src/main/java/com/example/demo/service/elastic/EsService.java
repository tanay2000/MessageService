package com.example.demo.service.elastic;



import com.example.demo.model.EsInput;
import com.example.demo.model.EsModel;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EsService {

    public void save(EsModel esModel);
    public Page<EsModel> findByMessage(String messageText,Optional<Integer> page);
    public Page<EsModel> findByDate(EsInput esInput, Optional<Integer> page);


}
