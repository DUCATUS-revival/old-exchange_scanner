package io.lastwill.eventscan.services.monitors;

import io.lastwill.eventscan.events.model.DucExchangeEvent;
import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.NetworkType;
import io.mywish.blockchain.WrapperOutput;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
//@Component
public class DucatusExchangeMonitor {
    @Value("${io.lastwill.eventscan.exchange.ducatus.addresses}")
    private List<String> ducatusExchangeAddresses;
    @Autowired
    private EventPublisher eventPublisher;

    @EventListener
    private void handleBtcBlock(NewBlockEvent event) {
        if (event.getNetworkType() != NetworkType.DUCATUS_MAINNET) {
            return;
        }
        Set<String> addresses = event.getTransactionsByAddress().keySet();
        if (addresses.isEmpty()) {
            return;
        }
        ducatusExchangeAddresses.stream().filter(addresses::contains)
                .forEach(exchangeAddress -> {
                    List<WrapperTransaction> txes = event.getTransactionsByAddress().get(exchangeAddress);
                    if (txes == null) {
                        log.warn("There is no exchange addresses found for Ducatus address {}.", exchangeAddress);
                        return;
                    }
                    for (WrapperTransaction tx: txes) {
                        for (WrapperOutput output: tx.getOutputs()) {
                            if (output.getParentTransaction() == null) {
                                log.warn("Skip it. Output {} has not parent transaction.", output);
                                continue;
                            }
                            if (!output.getAddress().equalsIgnoreCase(exchangeAddress)) {
                                continue;
                            }

                            eventPublisher.publish(new DucExchangeEvent(
                                    event.getNetworkType(),
                                    tx,
                                    exchangeAddress,
                                    output.getAddress(),
                                    output.getValue(),
                                    CryptoCurrency.DUC,
                                    true
                            ));
                        }
                    }
                });
    }
}
