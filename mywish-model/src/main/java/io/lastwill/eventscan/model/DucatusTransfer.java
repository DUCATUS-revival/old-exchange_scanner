package io.lastwill.eventscan.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "transfers_ducatustransfer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DucatusTransfer {
    @Id
    private long id;
    @Column(name = "tx_hash")
    private String txHash;
    @Column(name = "amount")
    private BigInteger amount;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private UserAddressExchange userAddressExchange;
    @Column(name = "state")
    private String state;
}
