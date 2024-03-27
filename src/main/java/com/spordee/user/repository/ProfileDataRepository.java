package com.spordee.user.repository;

import com.spordee.user.entity.objects.Achievements;
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
   @Query(value = "{ 'user_name' : ?0 }", fields = "{ 'user_name' : 1, 'instituteDetails' : 1 }")
   List<InstituteDetails> findInstituteDataByUserNameEquals(String username);

   @Query(value = "{'user_name': ?0}",fields = "{'user_name': 1,'name': 1,'birthDay': 1,'birth_country': 1,'phone_number': 1,'languages': 1,'place_of_birth': 1,'place_of_residence': 1,'citizenShip':1,'email': 1}")
   ProfileData findDetailsForUpdateByUserName(String username);

   @Query(value = "{'user_name':?0 }",fields = "{'achievements': 1}")
   List<Achievements> findAchievementsByUserName(String username);

}
