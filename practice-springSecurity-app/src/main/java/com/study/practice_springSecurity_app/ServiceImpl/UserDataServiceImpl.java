package com.study.practice_springSecurity_app.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.practice_springSecurity_app.Entity.UserData;
import com.study.practice_springSecurity_app.JwtRequest.UserJwtRequest;
import com.study.practice_springSecurity_app.JwtResponse.UserJwtResponse;
import com.study.practice_springSecurity_app.JwtService.UserService;
import com.study.practice_springSecurity_app.JwtUtil.JwtUtility;
import com.study.practice_springSecurity_app.Repo.UserRepo;
import com.study.practice_springSecurity_app.Service.UserDataService;

@Service
public class UserDataServiceImpl implements UserDataService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@Override
	public UserData signup(UserData user) {
		if(userRepo.existsById(user.getId())) {
			return null;
		}
		else {
			UserData data = new UserData();
			data.setId(user.getId());
			data.setUserName(user.getUserName());
			data.setEmail(user.getEmail());
			data.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			data.setRole(user.getRole());
			return userRepo.save(data);
		}
		
	}

	@Override
	public UserJwtResponse login(UserJwtRequest authRequest) {
		UserJwtResponse userData = new UserJwtResponse();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		
			UserData user = new UserData();
			if (authentication.isAuthenticated()) {	
				user = userRepo.findByEmail(authRequest.getEmail());
				userData.setSignupData(user);
				userData.setMessage("Login Sucessfull");
				userData.setJwtToken(jwtUtility.generateToken(userService.loadUserByUsername(authRequest.getEmail()))); 
				return userData;
			}
			else {
				userData.setSignupData(null);
				userData.setMessage("Password Incorrect");
				return userData;
			}
	}

	@Override
	public UserData getDataByid(int id) {
		if(! userRepo.existsById(id)) {
			return null;
		}
		return userRepo.findById(id).get();
	}

	@Override
	public List<UserData> getAllData() {
		List<UserData> allData= userRepo.findAll();
		if(allData.size()>0) {
			return allData;
		}
		return null;
	}

}
