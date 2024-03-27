package com.spordee.user.repository;

import com.spordee.user.entity.objects.InstituteDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileDataRepository extends MongoRepository<ProfileData,String> {
   ProfileData findByUserName(String username);
   @Query(value = "{ 'userName' : ?0 }", fields = "{ 'userName' : 1, 'instituteDetails' : 1 }")
   List<InstituteDetails> findInstituteDataByUserNameEquals(String username);

}
