package io.lastwill.eventscan.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exchange_requests_exchangerequest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAddressExchange {
    @Id
    private long id;
    @Column(name = "duc_address")
    private String ducAddress;
    @Column(name = "eth_address")
    private String ethAddress;
    @Column(name = "btc_address")
    private String btcAddress;
}
