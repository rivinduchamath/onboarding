package com.spordee.user.reactive.repository;

import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProfileRepository extends ReactiveMongoRepository<PrimaryUserDetails, String> {
}
