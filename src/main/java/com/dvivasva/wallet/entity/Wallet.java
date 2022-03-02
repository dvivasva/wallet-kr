package com.dvivasva.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@RedisHash("Wallet")
@Document("wallet-kr")
public class Wallet implements Serializable {
    @Id
    private String id;
    private String numberCard;
    private String numberPhone;
    private String imei;
    private String document;
    private String email;
    private int status; // used for blocking

}
