package com.dvivasva.wallet.controller;

import com.dvivasva.wallet.advice.PostNotFoundException;
import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.service.WalletService;
import com.dvivasva.wallet.utils.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final static Logger logger = LoggerFactory.getLogger(WalletController.class);
    private final WalletService walletService;

    /**
     * @param wallet .
     * @return status 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WalletDto> create(
            @RequestBody final Mono<WalletDto> wallet) {
        return walletService.create(wallet);
    }

    /**
     * @return flux .
     */
    @GetMapping
    public Flux<WalletDto> read() {
        return walletService.read();
    }

    /**
     * @param wallet .
     * @param id     .
     * @return monoDto.
     */
    @PutMapping("/{id}")
    @CachePut(key = "id",value = "Wallet")
    public Mono<WalletDto> update(@RequestBody final Mono<WalletDto> wallet, @PathVariable String id) {
        return walletService.update(wallet, id);
    }

    /**
     * @param id .
     * @return void delete.
     */
    @DeleteMapping("/{id}")
    @CacheEvict(key = "id", value = "Wallet")
    public Mono<Void> delete(@PathVariable final String id) {
        return walletService.delete(id);
    }


    /**
     * @param id .
     * @return mono.
     */
    @GetMapping("/{id}")
    @Cacheable(key = "id",value = "Wallet")
    public Mono<WalletDto> findById(@PathVariable final String id) {
        return walletService.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));

    }

    /**
     * @param numberPhone .
     * @return mono walletDto.
     */
    @GetMapping("/number-phone/{numberPhone}")
   @Cacheable(value = "Wallet", key = "numberPhone")
    public Mono<WalletDto> findByNumberPhone(@PathVariable final String numberPhone) {
        return walletService.findByNumberPhone(numberPhone)
                .switchIfEmpty(Mono.error(new PostNotFoundException(numberPhone)));
    }



}
