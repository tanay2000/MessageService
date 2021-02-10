package com.example.demo.repository;


import com.example.demo.model.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist,String> {
    @Query(value = "SELECT * FROM black_list",
            nativeQuery = true)
    List<String> findAllBlacklistedNumber();
}
