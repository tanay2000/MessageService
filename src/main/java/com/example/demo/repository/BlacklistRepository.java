package com.example.demo.repository;


import com.example.demo.model.BlacklistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<BlacklistEntity,String> {
    @Query(value = "SELECT * FROM black_list",
            nativeQuery = true)
    Page<String> findAllBlacklistedNumber(Pageable pageable);
}
