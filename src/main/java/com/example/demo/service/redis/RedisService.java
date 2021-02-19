package com.example.demo.service.redis;

import com.example.demo.model.BlackListInput;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface RedisService {

    public void addNumberToBlacklist(BlackListInput phoneNumbers);

    public void removeNumberFromBlacklist(String phoneNumber);

    public Page<String> findAllBlacklistedNumber(Optional<Integer> page);

    public boolean checkIfExist(String phoneNumber);
}
