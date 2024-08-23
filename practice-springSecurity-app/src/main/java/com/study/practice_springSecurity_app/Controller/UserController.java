package com.study.practice_springSecurity_app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.study.practice_springSecurity_app.Entity.UserData;
import com.study.practice_springSecurity_app.JwtRequest.UserJwtRequest;
import com.study.practice_springSecurity_app.JwtResponse.UserJwtResponse;
import com.study.practice_springSecurity_app.Service.UserDataService;

@RestController
public class UserController {
	
	@Autowired
	private UserDataService service;
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserData user) {
		UserData userData = service.signup(user);
		if(userData != null) {
			return ResponseEntity.status(200).body(userData);
		}
		return ResponseEntity.status(400).body("Not Signed up");
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserJwtRequest authRequest) {
		UserJwtResponse response =  service.login(authRequest);
		if(response.getSignupData() !=null) {
			return ResponseEntity.status(200).body(response);
		}
		return ResponseEntity.status(400).body(response.getMessage());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/getbyid")
	public ResponseEntity<?> getDataByid(@RequestHeader int id) {
		UserData userData = service.getDataByid(id);
		System.out.println(id);
		if(userData != null) {
			return ResponseEntity.status(200).body(userData);
		}
		return ResponseEntity.status(400).body("No data present");
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/getall")
	public ResponseEntity<?> getAllData(){
		List<UserData> userData = service.getAllData();
		if(userData != null) {
			return ResponseEntity.status(200).body(userData);
		}
		return ResponseEntity.status(400).body("No list found");
		
	}


}
