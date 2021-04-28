package com.authentication.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String profileId;
	private String accessToken;

	// need default constructor for JSON Parsing
	public JwtRequest() {

	}

	public JwtRequest(String profileId, String accessToken) {
		this.setProfileId(profileId);
		this.setAccessToken(accessToken);
	}



	public String getProfileId() {
		return this.profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}