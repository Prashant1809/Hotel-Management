package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "backup_codes")
public class BackupCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String code; 
    
    @Override
    public String toString() {
        return code;
    }

    // Getters and setters 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}




