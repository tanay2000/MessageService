
package com.example.demo.controller;

import com.example.demo.model.BlackListInput;
import com.example.demo.model.Blacklist;

import com.example.demo.response.data;
import com.example.demo.response.error;
import com.example.demo.service.redis.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/blacklist")
public class BlacklistController {

    @Autowired
    RedisService redisService;


    @PostMapping
//  @CachePut(value="Blacklist",key="#phoneNumber")
    public ResponseEntity<Object> addNumberToBlacklist(@RequestBody BlackListInput phone_numbers) {

        try {
            Blacklist blackList = new Blacklist();
            for (String phoneNumber : phone_numbers.getPhone_numbers()) {
                blackList.setPhoneNumber(phoneNumber);
                System.out.println(phoneNumber);
                redisService.addNumberToBlacklist(blackList, phoneNumber);

            }

            data Data = new data();
            Data.setComments("Successfully blacklisted");

            return new ResponseEntity<>(Data, HttpStatus.OK);
        } catch (Exception ex) {

            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/{phoneNumber}")
    public Blacklist isPresent(@PathVariable String phoneNumber) {

        return redisService.numberIsPresent(phoneNumber);
    }


    @GetMapping("/")
    public List<String> findAllBlacklistedNumber() {
        List<String> data = redisService.findAllBlacklistedNumber();
        return data;


    }

    @DeleteMapping("/{phoneNumber}")
    @CacheEvict(value = "Blacklist", key = "#phoneNumber", allEntries = true)
    public void removeNumberFromBlacklist(@PathVariable String phoneNumber) {
        redisService.removeNumberFromBlacklist(phoneNumber);
    }


}