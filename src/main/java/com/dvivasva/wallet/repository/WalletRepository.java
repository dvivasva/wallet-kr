package com.dvivasva.wallet.repository;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.util.WalletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WalletRepository {

    public static final String HASH_KEY = "Wallet";

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    public Mono create(final Mono<WalletDto> entityToDto) {

        Mono<WalletDto> result = entityToDto.map(
                p -> {
                    if (p.getId() != null) {
                        String id = UUID.randomUUID().toString();
                        p.setId(id);
                    }
                    return p;
                });

        return reactiveRedisTemplate.<String, Wallet>opsForHash().put(HASH_KEY, result.map(WalletDto::getId), result)
                .log()
                .map(p -> entityToDto);
    }

    public Flux<WalletDto> read() {
        ReactiveHashOperations<String, String, Wallet> val = reactiveRedisTemplate.opsForHash();
        return val.values(HASH_KEY).map(WalletUtil::entityToDto);
    }

    public Mono<Void> delete(String id) {
        return reactiveRedisTemplate.<String, Wallet>opsForHash().remove(HASH_KEY, id)
                .flatMap(p -> Mono.<Void>empty());
    }

    public Mono<WalletDto> findById(String id) {
        return reactiveRedisTemplate.<String, Wallet>opsForHash().get(HASH_KEY, id);
    }
}
