package com.spordee.user.repository;

import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrimaryUserDataRepository extends MongoRepository<PrimaryUserDetails, String>, QuerydslPredicateExecutor<PrimaryUserDetails> {
}
