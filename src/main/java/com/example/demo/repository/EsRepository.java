package com.example.demo.repository;



import com.example.demo.model.EsModel;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EsRepository extends ElasticsearchRepository<EsModel,String> {

    Page<EsModel> findByMessage(String text, Pageable pageable);
   Page<EsModel> findAllByCreatedAtBetween(long startEpoch,long endEpoch,Pageable pageable);

}
