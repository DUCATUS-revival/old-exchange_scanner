package io.lastwill.eventscan.services.handlers;

import io.lastwill.eventscan.events.model.TransferConfirmEvent;
import io.lastwill.eventscan.messages.DucTransferedNotify;
import io.lastwill.eventscan.services.ExternalNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBean(ExternalNotifier.class)
public class DucExchangeEventHandler {
    @Autowired
    private ExternalNotifier externalNotifier;

    @EventListener
    private void exchangeTransactionEventHandler(final TransferConfirmEvent event) {
        try {
            externalNotifier.send(
                    event.getNetworkType(),
                    new DucTransferedNotify(
                          event.getDucAddress(),
                            event.getTx_hash(),
                            event.getTransferId(),
                            event.isSuccess())
            );
        } catch (Throwable e) {
            log.error("Sending notification about new Ducatus exchange failed.", e);
        }
    }
}
