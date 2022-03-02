package com.dvivasva.wallet.repository;

import com.dvivasva.wallet.entity.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IWalletRepository extends ReactiveMongoRepository<Wallet, String> {
}
