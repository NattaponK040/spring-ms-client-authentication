package com.authentication.model;
  

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document; 
 

@Document
public class User {

	@Id
	private String id;
	private String profileId;
	private String email;
	private String accessToken;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	

	
}
