package io.lastwill.eventscan.events.model;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.NetworkType;
import io.mywish.blockchain.WrapperTransaction;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class DucExchangeEvent extends BaseEvent {
    private final WrapperTransaction transaction;
    private final String fromAddress;
    private final String exchangeAddress;
    private final BigInteger amount;
    private final CryptoCurrency currency;
    private final boolean isSuccess;

    public DucExchangeEvent(NetworkType networkType, WrapperTransaction tx, String exchangeAddress,
                            String fromAddress, BigInteger amount, CryptoCurrency currency, boolean isSuccess) {
        super(networkType);
        this.transaction = tx;
        this.exchangeAddress = exchangeAddress;
        this.fromAddress = fromAddress;
        this.amount = amount;
        this.currency = currency;
        this.isSuccess = isSuccess;
    }
}
