package io.lastwill.eventscan.services.monitors;

import io.lastwill.eventscan.events.model.TransferConfirmEvent;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.model.UserAddressExchange;
import io.lastwill.eventscan.repositories.DucatusTransferRepository;
import io.lastwill.eventscan.services.TransactionProvider;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransferMonitor extends AbstractMonitor {
    private final String state = "WAITING_FOR_CONFIRMATION";
    @Autowired
    private TransactionProvider transactionProvider;

    @Autowired
    private DucatusTransferRepository ducatusTransferRepository;

    @Autowired
    private EventPublisher eventPublisher;

    @Override
    protected boolean checkCondition(NewBlockEvent newBlockEvent) {
        return newBlockEvent.getNetworkType() == NetworkType.DUCATUS_MAINNET;
    }


    @Override
    protected void processBlockEvent(NewBlockEvent newBlockEvent) {
        ducatusTransferRepository.findAllByState(state)
                .forEach(transferEntry -> filterTransactionsByAddress(newBlockEvent, transferEntry.getUserAddressExchange().getDucAddress())
                        .forEach(transaction -> transactionProvider.getTransactionReceiptAsync(newBlockEvent.getNetworkType(), transaction)
                                .thenAccept(receipt -> {
                                    if (receipt.getTransactionHash().equalsIgnoreCase(transferEntry.getTxHash())) {
                                        UserAddressExchange exchange = transferEntry.getUserAddressExchange();
                                        log.info("address {} was refill {} amount - {}",
                                                exchange.getDucAddress(),
                                                transferEntry.getAmount(),
                                                transferEntry.getTxHash());
                                        eventPublisher.publish(new TransferConfirmEvent(transferEntry));
                                    }
                                })));
    }
}
