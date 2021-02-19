
package com.example.demo.controller;

import com.example.demo.model.BlackListInput;
import com.example.demo.repository.BlacklistRepository;
import com.example.demo.response.SuccessResponse;
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
            redisService.addNumberToBlacklist(phoneNumbers);
            SuccessResponse successResponse = new SuccessResponse();
            successResponse.setComments("Successfully blacklisted");

            return new ResponseEntity<>(successResponse, HttpStatus.OK);
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