package com.spordee.user.service.impl.get;

import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.dto.response.common.MetaData;
import com.spordee.user.dto.response.common.ReturnAllTableDataResponse;
import com.spordee.user.entity.objects.InstituteDetails;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.service.GetUserService;
import com.spordee.user.util.ResponseMethods;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final SportsRepository sportsRepository;
    private final PrimaryUserDataRepository primaryUserDataRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<CommonResponse> getAllData(CommonResponse commonResponse, String username) {
        Mono<ProfileData> profileDataMono = fetchProfileDetailsAsync(username).subscribeOn(Schedulers.boundedElastic());
        Mono<PrimaryUserDetails> primaryUserDetailsMono = fetchPrimaryDetailsAsync(username).subscribeOn(Schedulers.boundedElastic());
        Mono<UserSports> userSportsMono = fetchSportsDetailsAsync(username).subscribeOn(Schedulers.boundedElastic());

        return Mono.zip(profileDataMono, primaryUserDetailsMono, userSportsMono)
                .map(tuple3 -> {
                    ProfileData profileData = tuple3.getT1();
                    PrimaryUserDetails primaryUserDetails = tuple3.getT2();
                    UserSports userSports = tuple3.getT3();

                    ReturnAllTableDataResponse<Object> response = new ReturnAllTableDataResponse<>();
                    response.setProfileData(profileData);
                    response.setPrimaryData(primaryUserDetails);
                    response.setSportsData(userSports);
                    commonResponse.setData(response);

                    return commonResponse;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(throwable -> {
                    commonResponse.setStatus(StatusType.STATUS_FAIL);
                    commonResponse.setMeta(new MetaData(true, CommonMessages.REQUEST_FAIL, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch data"));
                    return Mono.just(commonResponse);
                });
    }
    private Mono<UserSports> fetchSportsDetailsAsync(String username) {
        return Mono.fromCallable(() -> sportsRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<PrimaryUserDetails> fetchPrimaryDetailsAsync(String username) {
        return Mono.fromCallable(() -> primaryUserDataRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ProfileData> fetchProfileDetailsAsync(String username) {
        return Mono.fromCallable(() -> profileDataRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
    }
    }