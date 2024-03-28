package com.spordee.user.service.impl.get;

import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.dto.response.common.MetaData;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.enums.CommonMessages;
import com.spordee.user.enums.StatusType;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.service.GetUserService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

import static com.spordee.user.util.ResponseMethods.profileDataIsNull;

@Service
@Slf4j
@AllArgsConstructor
public class GetUserServiceImpl implements GetUserService {

    private final ProfileDataRepository profileDataRepository;
    private final SportsRepository sportsRepository;
    private final PrimaryUserDataRepository primaryUserDataRepository;

    //    public Flux<CommonResponse> fetchCombinedData(CommonResponse commonResponse, String username) {
//        return fetchPrimaryDetailsAsync(username)
//                .flatMap(table1 ->
//                        Flux.zip(Flux.just(table1),
//                                ReturnAllTableDataResponse.builder()
//                                        .primaryData(table1)
//                                        .profileData(fetchProfileDetailsAsync(username))
//                                        .sportsData(fetchSportsDetailsAsync(username)).build());
//    }
    private Mono<ReturnAllTableDataResponse> getCombinedData(String username) {
        Mono<PrimaryUserDetails> primaryDataMono = fetchPrimaryDetailsAsync(username)
                .defaultIfEmpty(new PrimaryUserDetails());
        Mono<ProfileData> profileDataMono = fetchProfileDetailsAsync(username)
                .defaultIfEmpty(new ProfileData());
        Mono<UserSports> sportsDataMono = fetchSportsDetailsAsync(username)
                .defaultIfEmpty(new UserSports());

        return Mono.zip(primaryDataMono, profileDataMono, sportsDataMono).map(tuple -> {
            PrimaryUserDetails primaryData = tuple.getT1();
            ProfileData profileData = tuple.getT2();
            UserSports sportsData = tuple.getT3();

            ReturnAllTableDataResponse combinedDTO = new ReturnAllTableDataResponse();
            combinedDTO.setPrimaryData(primaryData);
            combinedDTO.setProfileData(profileData);
            combinedDTO.setSportsData(sportsData);
            return combinedDTO;
        });
    }


    @Override
    @Transactional(readOnly = true)
    public Mono<CommonResponse> getAllData(CommonResponse commonResponse, String username) {
        final CommonResponse finalCommonResponse = commonResponse; // Make a final copy
        return getCombinedData(username)
                .map(combinedData -> {
                    CommonResponse responseToUse = finalCommonResponse != null ? finalCommonResponse : new CommonResponse(); // Use the final copy or create a new instance
                    responseToUse.setData(combinedData);
                    return responseToUse;
                })
                .switchIfEmpty(Mono.defer(() ->
                        profileDataIsNull(commonResponse)));
    }
    private Mono<UserSports> fetchSportsDetailsAsync(String username) {
        return Mono.fromCallable(() -> sportsRepository.findByUserName(username)).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<PrimaryUserDetails> fetchPrimaryDetailsAsync(String username) {
        return Mono.fromCallable(() -> primaryUserDataRepository.findByUserName(username)).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ProfileData> fetchProfileDetailsAsync(String username) {
        return Mono.fromCallable(new Callable<ProfileData>() {
            @Override
            public ProfileData call() throws Exception {
                return profileDataRepository.findByUserName(username);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ReturnAllTableDataResponse {
    private ProfileData profileData;
    private PrimaryUserDetails primaryData;
    private UserSports sportsData;


}