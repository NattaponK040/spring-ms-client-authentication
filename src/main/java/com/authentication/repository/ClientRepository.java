package com.authentication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.authentication.model.User;

public interface ClientRepository extends MongoRepository<User, String> {
	
	@Query(value = "{'attributes.profileId' : ?0}", fields = "{ profileId : 1 }")
	String findProfileId(String profile);
	
	@Query(value = "{'attributes.accessToken' : ?0}", fields = "{ accessToken : 1 }")
	String findAccessToken(String atk);
	
	@Query(value = "{'attributes.email' : ?0}", fields = "{ email : 1 }")
	String findEmail(String email);
}
