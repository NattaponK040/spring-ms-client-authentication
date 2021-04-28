package com.authentication.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.authentication.repository.ClientRepository;

@Service
public class ProfileValidation {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public boolean isExistProfile(String profileId,String accesstoken) {
		String hasProfile = clientRepository.findProfileId(profileId);
		String hasActoken = clientRepository.findAccessToken(accesstoken);
		System.out.println(hasProfile+" "+hasActoken);
		return false;
	}
}
