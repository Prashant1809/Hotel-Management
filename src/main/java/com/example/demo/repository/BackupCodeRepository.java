package com.example.demo.repository;

import com.example.demo.entity.BackupCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackupCodeRepository extends JpaRepository<BackupCode, Long> {
    List<BackupCode> findByUserId(Long userId);
    BackupCode findByUserIdAndCode(Long userId, String code);
}
