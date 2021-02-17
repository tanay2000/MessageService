package com.example.demo.service.redis;

import com.example.demo.model.Blacklist;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BlacklistService {
     void addNumberToBlacklist(Blacklist blackList);
     void removeNumberFromBlacklist(String phoneNumber);
     boolean checkIfExist(String phoneNumber);
     Page<String> findAllBlacklistedNumber(Optional<Integer> page);
}
