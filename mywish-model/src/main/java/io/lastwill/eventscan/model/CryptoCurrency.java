package io.lastwill.eventscan.model;

import lombok.Getter;

@Getter
public enum CryptoCurrency {
    ETH(18),
    BTC(8),
    GAS(8),
    DUC(8),
    USD(6, "$");

    private final int decimals;
    private final String symbol;

    CryptoCurrency(int decimals, String symbol) {
        this.decimals = decimals;
        this.symbol = symbol;
    }

    CryptoCurrency(int decimals) {
        this(decimals, null);
    }

    @Override
    public String toString() {
        return symbol != null
                ? symbol
                : super.toString();
    }
}
