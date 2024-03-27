package com.spordee.user.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("token_store") // token_store Should Be equals to KeyspaceSettings in RedisConfiguration
public class TokenStorage {
    @Id

    private String id;

    private String deviceId;

    private boolean isAllDevice;

    private String userName;

    private String userType;

    private long createdTime;
}
