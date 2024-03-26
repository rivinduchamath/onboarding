package com.spordee.user.repository;

import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface PrimaryUserDataRepository extends MongoRepository<PrimaryUserDetails, String> {

    PrimaryUserDetails findByUserName(String username);

}
