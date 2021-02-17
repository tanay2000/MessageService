package com.example.demo.service.redis;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void addNumberToBlacklist(Blacklist blackList) {
        System.out.println("insert in db");
        redisTemplate.opsForSet().add(KEY,blackList.getPhoneNumber());
        blacklistService.addNumberToBlacklist(blackList);
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
            throw new NotFoundException("No not in blacklist");
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

