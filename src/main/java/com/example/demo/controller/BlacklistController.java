
package com.example.demo.controller;

import com.example.demo.exception.InvalidRequestException;
import com.example.demo.model.BlackListInput;
import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import com.example.demo.response.data;
import com.example.demo.response.error;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/v1/blacklist")
public class BlacklistController {

    @Autowired
    RedisService redisService;
    @Autowired
    BlacklistRepository blacklistRepository;

    @PostMapping
    public ResponseEntity<Object> addNumberToBlacklist(@RequestBody @Valid BlackListInput phoneNumbers) {
        try {
            Blacklist blackList = new Blacklist();
            for (String phoneNumber : phoneNumbers.getPhoneNumbers()) {
                if(phoneNumber.trim().isEmpty())
                    throw  new InvalidRequestException("Phone number can't be empty");
                blackList.setPhoneNumber(phoneNumber);
                System.out.println(phoneNumber);
                redisService.addNumberToBlacklist(blackList);

            }
            data Data = new data();
            Data.setComments("Successfully blacklisted");

            return new ResponseEntity<>(Data, HttpStatus.OK);
        } catch (Exception ex) {

            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);

        }

    }
    @DeleteMapping("/{phoneNumber}")
    public void removeNumberFromBlacklist(@PathVariable String phoneNumber) {
        redisService.removeNumberFromBlacklist(phoneNumber);
    }

    @GetMapping("/")
    public Page<String> findAllBlacklistedNumber(@RequestParam Optional<Integer> page) {
        return redisService.findAllBlacklistedNumber(page);
    }

}