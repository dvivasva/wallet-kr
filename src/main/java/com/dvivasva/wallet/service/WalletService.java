package com.dvivasva.wallet.service;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.repository.IWalletRepository;
import com.dvivasva.wallet.util.WalletUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final static Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final IWalletRepository iWalletRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<WalletDto> create(final Mono<WalletDto> entityToDto) {
        return entityToDto.map(WalletUtil::dtoToEntity)
                .flatMap(iWalletRepository::save)
                .map(WalletUtil::entityToDto);

    }

    public Flux<WalletDto> read() {
        return iWalletRepository.findAll().map(WalletUtil::entityToDto);
    }

    public Mono<WalletDto> update(
            final Mono<WalletDto> customerDtoMono, final String id) {
        return iWalletRepository.findById(id)
                .flatMap(p -> customerDtoMono.map(WalletUtil::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(iWalletRepository::save)
                .map(WalletUtil::entityToDto);
    }

    public Mono<Void> delete(final String id) {
        return iWalletRepository.deleteById(id);
    }


    public Mono<WalletDto> findByNumberPhone(String numberPhone) {
        logger.info("inside methode find by number Phone ");
        Query query = new Query();
        query.addCriteria(Criteria.where("numberPhone").is(numberPhone));
        return reactiveMongoTemplate.findOne(query, Wallet.class).map(WalletUtil::entityToDto);

    }

    public Mono<WalletDto> findById(final String id) {
        return iWalletRepository.findById(id).map(WalletUtil::entityToDto);
    }


}
