package com.authentication.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.configuration.JwtTokenUtil;
import com.authentication.model.JwtRequest;
import com.authentication.model.JwtResponse; 
 
@RestController
public class JwtAuthenticationController {
	
	private static final Logger sLogger = LoggerFactory.getLogger(JwtAuthenticationController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		sLogger.info("Client Authentication Request.");
		authenticate(authenticationRequest.getAccessToken(), authenticationRequest.getProfileId());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getAccessToken());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@GetMapping(path = "/aunjai-check/health")
	public ResponseEntity<String> health(){
		return new  ResponseEntity<String>("Success", HttpStatus.OK);
	}

	private void authenticate(String accesstoken, String profileId) throws Exception {
		Objects.requireNonNull(accesstoken);
		Objects.requireNonNull(profileId);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accesstoken, profileId));
		} catch (DisabledException e) {
			sLogger.error("USER_DISABLED", e);
		} catch (BadCredentialsException e) { 
			sLogger.error("INVALID_CREDENTIALS", e);
		}
	}
}

