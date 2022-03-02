package com.dvivasva.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class CardDto {
    private String id;
    private String type; // debt or credit
    private String number;
    private Date dueDate;
    private String cardVerificationValue;
    private String keyATM;
    private String keyInternet;
    private  String accountId;

}
