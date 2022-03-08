package com.dvivasva.wallet.repository;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.utils.WalletUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WalletRepository {


    private static final Logger logger = LoggerFactory.getLogger(WalletRepository.class);
    private static final String KEY = "Wallet";
    private final ReactiveRedisOperations<String, Wallet> redisOperations;
    private final ReactiveHashOperations<String, String, Wallet> hashOperations;


    @Autowired
    public WalletRepository(ReactiveRedisOperations<String, Wallet> redisOperations) {
        this.redisOperations = redisOperations;
        this.hashOperations = redisOperations.opsForHash();
    }

    public Mono<WalletDto> create(Wallet wallet) {
        logger.info("inside methode create");
        if (wallet.getId() != null) {
            String id = UUID.randomUUID().toString();
            wallet.setId(id);
        }
        return hashOperations.put(KEY, wallet.getId(), wallet)
                .map(isSaved -> wallet).map(WalletUtil::entityToDto);
    }

    public Mono<Boolean> existsById(String id) {
        return hashOperations.hasKey(KEY, id);
    }
    public Mono<WalletDto> update(Wallet wallet ,String id) {
        Mono<Boolean> booleanMono=existsById(id);
        return booleanMono.flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)) {
                        return hashOperations.put(KEY, wallet.getId(), wallet)
                                .map(isSaved -> wallet);
                       /* return Mono.error(new DuplicateKeyException("Duplicate key, numberCard: " +
                                wallet.getNumberCard() + " or numberPhone: " + wallet.getNumberPhone() + " exists."));*/
                    } else {
                        return hashOperations.put(KEY, wallet.getId(), wallet)
                                .map(isSaved -> wallet);
                    }
                })
                .thenReturn(wallet).map(WalletUtil::entityToDto);
    }

    public Flux<WalletDto> read() {
        return hashOperations.values(KEY).map(WalletUtil::entityToDto);
    }

    public Mono<Void> delete(String id) {
        return hashOperations.remove(KEY, id).then();
    }

    public Mono<WalletDto> findById(String id) {
        return hashOperations.get(KEY, id).map(WalletUtil::entityToDto);
    }

    public Mono<WalletDto> findByNumberCard(String numberCard) {
        return hashOperations.values(KEY)
                .filter(w -> w.getNumberCard().equals(numberCard))
                .singleOrEmpty().map(WalletUtil::entityToDto);
    }

    public Mono<WalletDto> findByNumberPhone(String numberPhone) {
        return hashOperations.values(KEY)
                .filter(w -> w.getNumberPhone().equals(numberPhone))
                .singleOrEmpty().map(WalletUtil::entityToDto);
    }


}
