package com.study.practice_springSecurity_app.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.study.practice_springSecurity_app.Entity.UserData;

public interface UserRepo extends JpaRepository<UserData,Integer>{
	
	public UserData findByEmail(String email);
	
	public Optional<UserData> findByEmailAndPassword(String email, String password);
	
	@Query(value = "SELECT * FROM users WHERE email = :data OR user_name = :data", nativeQuery = true) 
	public UserData findByUsernameOrEmail(String data);

}
