package com.example.demo.service.redis;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.BlackListInput;
import com.example.demo.model.BlacklistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RedisServiceImpl implements RedisService{

    private static final String KEY = "BLACKLIST";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    BlacklistService blacklistService;

    @Override
    public void addNumberToBlacklist(BlackListInput phoneNumbers) {
        System.out.println("insert in db");
        BlacklistEntity blacklistEntity = new BlacklistEntity();
        for (String phoneNumber : phoneNumbers.getPhoneNumbers()) {
            blacklistEntity.setPhoneNumber(phoneNumber);
            if(!blacklistEntity.getPhoneNumber().isEmpty()) {
                redisTemplate.opsForSet().add(KEY, blacklistEntity.getPhoneNumber());
                blacklistService.addNumberToBlacklist(blacklistEntity);
            }
        }

    }

    @Override
    public void removeNumberFromBlacklist(String phoneNumber) {
        System.out.println("delete from db");
        try {

            if(!checkIfExist(phoneNumber))
                throw new NotFoundException("Number not in blacklist");
            blacklistService.removeNumberFromBlacklist(phoneNumber);
            redisTemplate.opsForSet().remove(KEY, phoneNumber);
        }
        catch (Exception exception){
            throw exception;
        }
    }

    @Override
    public Page<String> findAllBlacklistedNumber(Optional<Integer> page) {
        return blacklistService.findAllBlacklistedNumber(page);

    }


    @Override
    public boolean checkIfExist(String phoneNumber){
        if(redisTemplate.opsForSet().isMember(KEY,phoneNumber))
            return true;
        return blacklistService.checkIfExist(phoneNumber);

    }
}

