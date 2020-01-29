package io.lastwill.eventscan.model;

import lombok.Getter;


@Getter
public enum NetworkType {
    ETHEREUM_MAINNET(NetworkProviderType.WEB3),
    ETHEREUM_ROPSTEN(NetworkProviderType.WEB3),
    BTC_MAINNET(NetworkProviderType.BTC),
    BTC_TESTNET_3(NetworkProviderType.BTC),
    DUCATUS_MAINNET(NetworkProviderType.DUCATUS),
    DUCATUS_TESTNET(NetworkProviderType.DUCATUS),
    DUCATUSX_MAINNET(NetworkProviderType.DUCATUSX),
    DUCATUSX_TESTNET(NetworkProviderType.DUCATUSX),
    ;
    public final static String ETHEREUM_MAINNET_VALUE = "ETHEREUM_MAINNET";
    public final static String ETHEREUM_ROPSTEN_VALUE = "ETHEREUM_ROPSTEN";
    public final static String BTC_MAINNET_VALUE = "BTC_MAINNET";
    public final static String BTC_TESTNET_3_VALUE = "BTC_TESTNET_3";
    public final static String DUCATUS_MAINNET_VALUE = "DUCATUS_MAINNET";
    public final static String DUCATUS_TESTNET_VALUE = "DUCATUS_TESTNET";
    public final static String DUCATUSX_MAINNET_VALUE = "DUCATUSX_MAINNET";
    public final static String DUCATUSX_TESTNET_VALUE = "DUCATUSX_TESTNET";

    private final NetworkProviderType networkProviderType;

    NetworkType(NetworkProviderType networkProviderType) {
        this.networkProviderType = networkProviderType;
    }

}
