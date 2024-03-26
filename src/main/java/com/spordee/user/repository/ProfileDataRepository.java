package com.spordee.user.repository;

import com.spordee.user.entity.profiledata.ProfileData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileDataRepository extends MongoRepository<ProfileData,String> {

   ProfileData findByUserName(String username);

}