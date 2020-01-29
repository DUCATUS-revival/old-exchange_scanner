package io.lastwill.eventscan.services.monitors.payments;

import io.lastwill.eventscan.events.model.UserPaymentEvent;
import io.lastwill.eventscan.model.CryptoCurrency;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.repositories.UserAddressExchangeRepository;
import io.mywish.blockchain.WrapperOutput;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class BtcPaymentMonitor {
    @Autowired
    private UserAddressExchangeRepository userAddressExchangeRepository;
    @Autowired
    private EventPublisher eventPublisher;

    @EventListener
    private void handleBtcBlock(NewBlockEvent event) {
        if (event.getNetworkType() != NetworkType.BTC_MAINNET) {
            return;
        }
        Set<String> addresses = event.getTransactionsByAddress().keySet();
        if (addresses.isEmpty()) {
            return;
        }
        userAddressExchangeRepository.findByBtcAddressesList(addresses)
                .forEach(userAddressExchange -> {
                    List<WrapperTransaction> txes = event.getTransactionsByAddress().get(userAddressExchange.getBtcAddress());
                    if (txes == null) {
                        log.warn("There is no userAddressExchange entity found for BTC address {}.", userAddressExchange.getBtcAddress());
                        return;
                    }
                    for (WrapperTransaction tx: txes) {
                        for (WrapperOutput output: tx.getOutputs()) {
                            if (output.getParentTransaction() == null) {
                                log.warn("Skip it. Output {} has not parent transaction.", output);
                                continue;
                            }
                            if (!output.getAddress().equalsIgnoreCase(userAddressExchange.getBtcAddress())) {
                                continue;
                            }

                            eventPublisher.publish(new UserPaymentEvent(
                                    event.getNetworkType(),
                                    tx,
                                    output.getValue(),
                                    CryptoCurrency.BTC,
                                    true,
                                    userAddressExchange
                            ));
                        }
                    }
                });
    }
}
