package com.dvivasva.wallet.component;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WalletComponent {

    private final WalletRepository walletRepository;

    public Mono<WalletDto> create(Wallet wallet) {
        return walletRepository.create(wallet);
    }

    public Flux<WalletDto> read() {
        return walletRepository.read();
    }

    public Mono<WalletDto> update(Wallet wallet, String id) {
        return walletRepository.update(wallet, id);
    }

    public Mono<Void> delete(String id) {
        return walletRepository.delete(id);
    }

    public Mono<WalletDto> findByNumberPhone(String numberPhone) {
       return  walletRepository.findByNumberPhone(numberPhone);
    }

    public Mono<WalletDto> findByNumberCard(String numberCard) {
        return walletRepository.findByNumberCard(numberCard);
    }
    public Mono<WalletDto> findById(String id) {
        return walletRepository.findById(id);
    }


}
