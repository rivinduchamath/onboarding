package com.spordee.user.configurations.redis;

import com.spordee.user.entity.TokenStorage;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.core.convert.MappingConfiguration;
import org.springframework.data.redis.core.index.IndexConfiguration;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import java.util.List;

@Configuration
public class RedisConfiguration extends KeyspaceConfiguration{

    @Bean
    public RedisMappingContext keyValueMappingContext() {
        return new RedisMappingContext(new MappingConfiguration(new IndexConfiguration(), new MyKeyspaceConfiguration()));
    }

    public static class MyKeyspaceConfiguration extends KeyspaceConfiguration {
        @NonNull
        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            KeyspaceSettings keyspaceSettings = new KeyspaceSettings(TokenStorage.class, "token_store"); // token_store Should Be equals to Entity Class
            keyspaceSettings.setTimeToLive(2592000L); // 35 days
            return List.of(keyspaceSettings);
        }
    }
}