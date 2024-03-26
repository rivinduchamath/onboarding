package com.spordee.user.repository;

import com.spordee.user.entity.sportsuserdata.UserSports;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsRepository  extends MongoRepository<UserSports,String> {
    UserSports findByUserName(String username);
}
