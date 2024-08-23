package com.study.practice_springSecurity_app.JwtResponse;

import com.study.practice_springSecurity_app.Entity.UserData;

public class UserJwtResponse {

	private UserData signupData;
	private String message;
	private String jwtToken;

	public UserJwtResponse(UserData signupData, String message, String jwtToken) {
		super();
		this.signupData = signupData;
		this.message = message;
		this.jwtToken = jwtToken;
	}

	public UserJwtResponse() {
		super();
	}

	public UserData getSignupData() {
		return signupData;
	}

	public void setSignupData(UserData signupData) {
		this.signupData = signupData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	@Override
	public String toString() {
		return "UserJwtResponse [signupData=" + signupData + ", message=" + message + ", jwtToken=" + jwtToken + "]";
	}
}
