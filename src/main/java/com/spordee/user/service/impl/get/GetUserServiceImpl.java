package com.spordee.user.service.impl.get;

import com.spordee.user.dto.response.common.CommonResponse;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import com.spordee.user.entity.profiledata.ProfileData;
import com.spordee.user.entity.sportsuserdata.UserSports;
import com.spordee.user.repository.PrimaryUserDataRepository;
import com.spordee.user.repository.ProfileDataRepository;
import com.spordee.user.repository.SportsRepository;
import com.spordee.user.service.GetUserService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.spordee.user.util.ResponseMethods.*;

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
    public Mono<CommonResponse> getUsersAllData(CommonResponse commonResponse, String username) {
        final CommonResponse finalCommonResponse = commonResponse; // Make a final copy
        return getCombinedData(username)
                .map(combinedData -> {
                    CommonResponse responseToUse = finalCommonResponse != null ? finalCommonResponse : new CommonResponse(); // Use the final copy or create a new instance
                    responseToUse.setData(combinedData);
                    return profileDataIsSuccessWhenGet(responseToUse);
                })
                .switchIfEmpty(Mono.defer(() ->
                        profileDataIsNullWhenGet(commonResponse)));
    }

//    @Override
//    public Mono<CommonResponse> getAllData(CommonResponse commonResponseMono) {
//        return commonResponseMono
//                .flatMapMany(commonResponse ->
//                        primaryUserDataRepository.findAllc() // Replace with your actual data access method
//                )
//                .collectList() // Collect all data into a Flux
//                .map(dataList -> ResponseEntity.ok().body(Flux.fromIterable(dataList))) // Create successful response
//                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(Flux.just(new CommonResponse())))); // Handle errors gracefully
//    }

    private Mono<UserSports> fetchSportsDetailsAsync(String username) {
        return Mono.fromCallable(() -> sportsRepository.findByUserName(username)).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<PrimaryUserDetails> fetchPrimaryDetailsAsync(String username) {
        return Mono.fromCallable(() -> primaryUserDataRepository.findByUserName(username)).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ProfileData> fetchProfileDetailsAsync(String username) {
        return Mono.fromCallable(() ->
                profileDataRepository.findByUserName(username))
                .subscribeOn(Schedulers.boundedElastic());
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