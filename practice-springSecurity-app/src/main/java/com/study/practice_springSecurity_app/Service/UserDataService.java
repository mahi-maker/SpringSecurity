package com.study.practice_springSecurity_app.Service;

import java.util.List;

import com.study.practice_springSecurity_app.Entity.UserData;
import com.study.practice_springSecurity_app.JwtRequest.UserJwtRequest;
import com.study.practice_springSecurity_app.JwtResponse.UserJwtResponse;

public interface UserDataService {
	
	public UserData signup(UserData user);
	public UserJwtResponse login(UserJwtRequest authRequest);
	public UserData getDataByid(int id);
	public List<UserData> getAllData();

}
