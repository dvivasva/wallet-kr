package com.dvivasva.wallet.dto;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletDto {
    private String id;
    private String numberCard;
    private String numberPhone;
    private String imei;
    private String document;
    private String email;
    private int status; // used for blocking
}
