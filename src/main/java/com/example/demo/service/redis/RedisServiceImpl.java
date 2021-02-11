package com.example.demo.service.redis;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService{

    private static final String KEY = "BLACKLIST";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    BlacklistRepository blacklistRepository;

    @Override
    public void addNumberToBlacklist(Blacklist blackList,String phoneNumber) {
        System.out.println("insert in db");
        redisTemplate.opsForSet().add(KEY,phoneNumber);
        blacklistRepository.save(blackList);
    }

    @Override
    public void removeNumberFromBlacklist(String phoneNumber) {
        System.out.println("delete from db");
        try {
            redisTemplate.opsForSet().remove(KEY, phoneNumber);
            blacklistRepository.deleteById(phoneNumber);
        }
        catch (Exception exception){
            throw new NotFoundException("No not in blacklist");
        }
    }

    @Override
    public List<String> findAllBlacklistedNumber() {
        return blacklistRepository.findAllBlacklistedNumber();
//        return redisTemplate.opsForSet().members(KEY);
    }

    @Override
    public Blacklist numberIsPresent(String phoneNumber)
    {
        System.out.println("ia am from db "+phoneNumber);
        return blacklistRepository.findById(phoneNumber).get();
    }

    @Override
    public boolean checkIfExist(String phoneNumber){
        if(redisTemplate.opsForSet().isMember(KEY,phoneNumber))
            return true;
        System.out.println("avvobwebvoweobv");
        return blacklistRepository.existsById(phoneNumber);

    }
}

