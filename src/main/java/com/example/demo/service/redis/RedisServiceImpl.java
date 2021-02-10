package com.example.demo.service.redis;


import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    BlacklistRepository blacklistRepository;


    @Override
//    @Cacheable(value="Blacklist",key="#phoneNumber")
    public Blacklist numberIsPresent(String phoneNumber)
    {
        System.out.println("ia am from db "+phoneNumber);
        return blacklistRepository.findById(phoneNumber).get();
    }

    @Override
    @CachePut(value="Blacklist",key="#phoneNumber")
    public void addNumberToBlacklist(Blacklist blackList,String phoneNumber) {
        System.out.println("insert in db");
        blacklistRepository.save(blackList);
    }

    @Override
    public void removeNumberFromBlacklist(String phoneNumber) {
        System.out.println("delete from db");
        blacklistRepository.deleteById(phoneNumber);
    }

    @Override

    public List<String> findAllBlacklistedNumber() {
        return blacklistRepository.findAllBlacklistedNumber();
    }


    @Override
    @Cacheable(value="Blacklist",key="#phoneNumber")
    public Blacklist checkIfExist(String phoneNumber){
        return blacklistRepository.getOne(phoneNumber);
    }
}
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Set;
//@Service
//public class RedisServiceImpl implements  RedisService{
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private static final String KEY = "BLACKLIST";
//
//    @Override
//    public void addNumberToBlacklist(String phoneNo) {
//        redisTemplate.opsForSet().add(KEY,phoneNo);
//    }
//
//    @Override
//    public void removeNumberFromBlacklist(String phoneNo) {
//        redisTemplate.opsForSet().remove(KEY,phoneNo);
//    }
//
//    @Override
//    public Set showAllPhoneNo() {
//        return redisTemplate.opsForSet().members(KEY);
//    }
//
//    @Override
//    public boolean checkIfExist(String phoneNo) {
//        return redisTemplate.opsForSet().isMember(KEY,phoneNo);
//    }
//
//
//}
