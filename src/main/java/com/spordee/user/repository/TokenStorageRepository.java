package com.spordee.user.repository;

import com.spordee.user.entity.TokenStorage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenStorageRepository extends CrudRepository<TokenStorage, String> {
    Optional<TokenStorage> deleteTokenStorageById(String s);

    void findByUserName(String mobileNumber);
}
