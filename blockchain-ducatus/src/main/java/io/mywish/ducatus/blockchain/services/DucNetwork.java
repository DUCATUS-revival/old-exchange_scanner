package io.mywish.ducatus.blockchain.services;

import com.neemre.btcdcli4j.core.client.BtcdClient;
import io.lastwill.eventscan.model.NetworkType;
import io.mywish.blockchain.WrapperBlock;
import io.mywish.blockchain.WrapperNetwork;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.blockchain.WrapperTransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class DucNetwork extends WrapperNetwork {
    private final BtcdClient ducClient;

    @Autowired
    private WrapperBlockDucService blockBuilder;

    public DucNetwork(NetworkType type, BtcdClient ducClient) {
        super(type);
        this.ducClient = ducClient;
    }

    @Override
    public Long getLastBlock() throws Exception {
        return ducClient.getBlockCount().longValue();
    }

    @Override
    public WrapperBlock getBlock(String hash) throws Exception {
        long height = ducClient.getBlock(hash).getHeight();
        return blockBuilder.build(ducClient.getBlock(hash), height);
    }

    @Override
    public WrapperBlock getBlock(Long number) throws Exception {
        String hash = ducClient.getBlockHash(number.intValue());
        return getBlock(hash);
    }

    @Override
    public BigInteger getBalance(String address, Long blockNo) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public WrapperTransactionReceipt getTxReceipt(WrapperTransaction transaction) {
        throw new UnsupportedOperationException("Method not supported");
    }
}
