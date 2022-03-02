package com.dvivasva.wallet.config;

import com.dvivasva.wallet.entity.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.*;

@Configuration
@EnableRedisRepositories
public class WalletConfig {

   @Bean
   public LettuceConnectionFactory lettuceConnectionFactory() {
       RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
       redisStandaloneConfig.setHostName("localhost");
       redisStandaloneConfig.setPort(6379);
       //redisStandaloneConfig.setPassword(redisPassword);
       return new LettuceConnectionFactory(redisStandaloneConfig);
   }

    @Bean
    public ReactiveRedisOperations<String, Wallet> redisOperations(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<String, Wallet> serializationContext = RedisSerializationContext
                .<String, Wallet>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new GenericToStringSerializer<>(Wallet.class))
                .hashKey(new StringRedisSerializer())
                .hashValue(new GenericJackson2JsonRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
}
