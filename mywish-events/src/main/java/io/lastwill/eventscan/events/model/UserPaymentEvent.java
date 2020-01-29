package io.lastwill.eventscan.events.model;

import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.UserAddressExchange;
import io.mywish.blockchain.WrapperTransaction;
import io.lastwill.eventscan.model.NetworkType;
import lombok.Getter;
import java.math.BigInteger;

@Getter
public class UserPaymentEvent extends PaymentEvent {
    private final UserAddressExchange userAddressExchange;

    public UserPaymentEvent(NetworkType networkType, WrapperTransaction transaction, BigInteger amount, CryptoCurrency currency, boolean isSuccess, UserAddressExchange userAddressExchange) {
        super(networkType, transaction, userAddressExchange.getEthAddress(), amount, currency, isSuccess);
        this.userAddressExchange = userAddressExchange;
    }
}
