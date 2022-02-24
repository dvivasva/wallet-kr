package com.dvivasva.wallet.repository;

import com.dvivasva.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WalletRepository {

    public static final String HASH_KEY = "Wallet";

    private final RedisTemplate template;



    public Flux<Wallet> read(){
        List<Wallet> list = new ArrayList<>();
        list.add(new Wallet("001","CAR"));

        return Flux.just(list);
    }
}
