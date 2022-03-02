package com.dvivasva.wallet.util;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class DefaultLoad implements CommandLineRunner {

    private final WalletService walletService;

    @Override
    public void run(String... args) throws Exception {

        WalletDto walletOne = new WalletDto("WAL-001", "4214-1001", "985783209", "IMEI-001", "752342467", "@gmail.co", 0);
        walletService.create(Mono.just(walletOne));
        WalletDto walletTwo = new WalletDto("WAL-002", "4214-1002", "985673209", "IMEI-002", "767342467", "@gmail.co", 0);
        walletService.create(Mono.just(walletTwo));
        WalletDto walletThree = new WalletDto("WAL-003", "4214-1003", "985603209", "IMEI-003", "722342467", "@gmail.co", 0);
        walletService.create(Mono.just(walletThree));
        WalletDto walletFour = new WalletDto("WAL-004", "4214-1004", "985613209", "IMEI-004", "732342467", "@gmail.co", 0);
        walletService.create(Mono.just(walletFour));
        WalletDto walletFive = new WalletDto("WAL-005", "4214-1005", "985683409", "IMEI-005", "762382467", "@gmail.co", 0);
        walletService.create(Mono.just(walletFive));
        WalletDto walletSix = new WalletDto("WAL-006", "4214-1006", "985683509", "IMEI-006", "762372467", "@gmail.co", 0);
        walletService.create(Mono.just(walletSix));
        WalletDto walletSeven = new WalletDto("WAL-007", "4214-1007", "995683209", "IMEI-007", "752342467", "@gmail.co", 0);
        walletService.create(Mono.just(walletSeven));
        WalletDto walletEight = new WalletDto("WAL-008", "4214-1008", "989683209", "IMEI-008", "762332467", "@gmail.co", 0);
        walletService.create(Mono.just(walletEight));
        WalletDto walletNine = new WalletDto("WAL-009", "4214-1009", "985583209", "IMEI-009", "762347467", "@gmail.co", 0);
        walletService.create(Mono.just(walletNine));
        WalletDto walletTen = new WalletDto("WAL-010", "4214-1010", "985483209", "IMEI-010", "762342967", "@gmail.co", 0);
        walletService.create(Mono.just(walletTen));
    }
}
