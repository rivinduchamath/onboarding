package com.spordee.user.repository;

import com.spordee.user.entity.primaryUserData.cascadetables.UserImages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends MongoRepository <UserImages,String> {
}
