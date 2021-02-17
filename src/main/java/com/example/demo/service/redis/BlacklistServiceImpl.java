package com.example.demo.service.redis;

import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlacklistServiceImpl implements BlacklistService{
    @Autowired
    private BlacklistRepository blacklistRepository;
    @Override
    public void addNumberToBlacklist(Blacklist blackList) {
        blacklistRepository.save(blackList);
    }

    @Override
    public void removeNumberFromBlacklist(String phoneNumber) {
        blacklistRepository.deleteById(phoneNumber);
    }

    @Override
    public boolean checkIfExist(String phoneNumber) {
        return blacklistRepository.existsById(phoneNumber);
    }
    @Override
    public Page<String> findAllBlacklistedNumber(Optional<Integer> page) {
        return blacklistRepository.findAllBlacklistedNumber(PageRequest.of(page.orElse(0),5 ));
    }

}
