package com.dvivasva.wallet.utils;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Wallet;
import org.springframework.beans.BeanUtils;

public final class WalletUtil {
    private WalletUtil() {
    }

    public static WalletDto entityToDto(final Wallet wallet) {
        var walletDto = new WalletDto();
        BeanUtils.copyProperties(wallet, walletDto);
        return walletDto;
    }
    public static Wallet dtoToEntity(final WalletDto walletDto) {
        var entity = new Wallet();
        BeanUtils.copyProperties(walletDto, entity);
        return entity;
    }


}
