



package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BackupCode;
import com.example.demo.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	   User findByUserNameOrEmail(String userName, String email);
	   
	   User findByEmail(String email);

	

}
