package io.mywish.ducatus.blockchain.services;

import io.mywish.blockchain.WrapperOutput;
import io.mywish.blockchain.WrapperTransaction;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class WrapperTransactionDucService {
    public WrapperTransaction build(String txId) {
        return new WrapperTransaction(
                txId,
                Collections.emptyList(),
                Collections.emptyList(),
                false
        );
    }
}
