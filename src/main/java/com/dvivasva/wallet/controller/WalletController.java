package com.dvivasva.wallet.controller;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

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
        return walletService.findById(id);
    }

    /**
     * @param numberPhone .
     * @return mono walletDto.
     */
    @GetMapping("/{numberPhone}")
    @Cacheable(value = "Wallet", key = "numberPhone")
    public Mono<WalletDto> findByNumberPhone(@PathVariable final String numberPhone) {
        return walletService.findByNumberPhone(numberPhone);
    }


}
