package io.lastwill.eventscan.services.monitors;

import io.lastwill.eventscan.events.model.TransferConfirmEvent;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.repositories.DucatusTransferRepository;
import io.mywish.blockchain.WrapperTransaction;
import io.mywish.scanner.model.NewBlockEvent;
import io.mywish.scanner.services.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransitionMonitor {
    private final NetworkType network;
    @Autowired
    private EventPublisher eventPublisher;
    protected final DucatusTransferRepository transferRepository;

    public TransitionMonitor(
            DucatusTransferRepository transferRepository
    ) {
        this.network = NetworkType.DUCATUS_MAINNET;
        this.transferRepository = transferRepository;
    }

    @EventListener
    public void onBlock(NewBlockEvent newBlockEvent) {
        if (newBlockEvent.getNetworkType() == this.network) {
            processBlockEvent(newBlockEvent);
        }
    }

    protected void processBlockEvent(NewBlockEvent newBlockEvent) {
        List<String> txHashes = getTxHashes(newBlockEvent);
        transferRepository
                .findAllByState("WAITING_FOR_CONFIRMATION")
                .stream()
                .filter(entry -> txHashes.contains(entry.getTxHash()))
                .forEach(entry -> {
                    log.info("{}: Transaction {} confirmed", this.getClass().getSimpleName(), entry.getTxHash());
                    eventPublisher.publish(new TransferConfirmEvent(entry));
                });
    }


    protected List<String> getTxHashes(NewBlockEvent newBlockEvent) {
        return newBlockEvent
                .getTransactionsByAddress()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .map(WrapperTransaction::getHash)
                .distinct()
                .collect(Collectors.toList());
    }
}