package com.direct2web.citysipuser.adapters.RestaurentAdapter.Account;

public class ResponseUserDetails{
	private String profile;
	private String name;
	private String mobile;
	private boolean error;
	private String message;
	private String email;

	public String getProfile(){
		return profile;
	}

	public String getName(){
		return name;
	}

	public String getMobile(){
		return mobile;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}

	public String getEmail(){
		return email;
	}
}
