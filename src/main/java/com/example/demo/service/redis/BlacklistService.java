package com.example.demo.service.redis;

import com.example.demo.model.BlacklistEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BlacklistService {
     void addNumberToBlacklist(BlacklistEntity blackList);
     void removeNumberFromBlacklist(String phoneNumber);
     boolean checkIfExist(String phoneNumber);
     Page<String> findAllBlacklistedNumber(Optional<Integer> page);
}
