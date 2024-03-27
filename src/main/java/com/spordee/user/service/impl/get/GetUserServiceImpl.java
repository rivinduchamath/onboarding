package com.spordee.user.service.impl.get;

import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.dto.response.common.InstitutionsResponseDto;
import com.spordee.user.entity.objects.InstituteDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.service.GetUserService;
import com.spordee.user.util.ResponseMethods;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static com.spordee.user.util.ResponseMethods.*;

@Service
@Slf4j
@AllArgsConstructor
public class GetUserServiceImpl implements GetUserService {

    private final ProfileDataRepository profileDataRepository;

    private Mono<List<InstituteDetails>> findInstituteDataAsync(String username) {
        return Mono.fromCallable(() -> profileDataRepository.findInstituteDataByUserNameEquals(username))
                .subscribeOn(Schedulers.boundedElastic());
    }
    @Override
    @Transactional(readOnly = true)
    public Mono<CommonResponse> getInstitutions(CommonResponse commonResponse, String username) {
        return Mono.defer(() -> Mono.fromCallable(() -> findInstituteDataAsync(username)))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(optionalProfileData -> {
                    if (optionalProfileData  != null) {
                        log.info("LOG:: UpdateUserServiceImpl getInstitutions profileData username: {}",username);
                        return ResponseMethods.getInstitutions(optionalProfileData, commonResponse)
                                .subscribeOn(Schedulers.boundedElastic());
                    } else {
                        log.info("LOG:: getInstitutions Not Found for username: {}", username);
                        return primaryUserDataIsNull(commonResponse);
                    }
                })
                .switchIfEmpty(Mono.defer(() -> userNotFound(commonResponse)))
                .subscribeOn(Schedulers.boundedElastic()); // Ensure entire chain runs on bounded elastic scheduler
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CommonResponse> getPersonalDetails(CommonResponse commonResponse, String username) {
        return Mono.defer(() -> Mono.fromCallable(() -> fetchPersonalDetailsAsync(username)))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(optionalProfileData -> {
                    if (optionalProfileData  != null) {
                        log.info("LOG:: UpdateUserServiceImpl getPersonalDetails profileData username: {}",username);
//                        return ResponseMethods.getPersonalDetails(optionalProfileData, commonResponse)
//                                .subscribeOn(Schedulers.boundedElastic());
                        return null;
                    } else {
                        log.info("LOG:: getPersonalDetails Not Found for username: {}", username);
                        return primaryUserDataIsNull(commonResponse);
                    }
                })
                .switchIfEmpty(Mono.defer(() -> userNotFound(commonResponse)))
                .subscribeOn(Schedulers.boundedElastic()); // Ensure entire chain runs on bounded elastic scheduler
    }

    private Object fetchPersonalDetailsAsync(String username) {
        return Mono.fromCallable(() -> profileDataRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
    }

}