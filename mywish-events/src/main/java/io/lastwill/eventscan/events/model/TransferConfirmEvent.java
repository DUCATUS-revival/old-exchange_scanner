package io.lastwill.eventscan.events.model;

import io.lastwill.eventscan.model.DucatusTransfer;
import io.lastwill.eventscan.model.NetworkType;
import lombok.Getter;

@Getter
public class TransferConfirmEvent extends BaseEvent {
    private final String tx_hash;
    private final long transferId;
    private final String ducAddress;
    private final String type = "transferred";
    private final boolean isSuccess;

    public TransferConfirmEvent(DucatusTransfer ducatusTransfer) {
        super(NetworkType.DUCATUS_MAINNET);
        this.tx_hash = ducatusTransfer.getTxHash();
        this.transferId = ducatusTransfer.getId();
        this.ducAddress = ducatusTransfer.getUserAddressExchange().getDucAddress();
        this.isSuccess = true;
    }

}
