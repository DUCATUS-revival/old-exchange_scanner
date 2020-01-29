package io.lastwill.eventscan.messages;

public class DucTransferedNotify extends BaseNotify {
    private final String ducAddress;
    private final long transferId;
    private final boolean isSuccess;

    public DucTransferedNotify(String ducAddress, String txHash, long transferId, boolean isSuccess) {
        super(PaymentStatus.COMMITTED, txHash);
        this.ducAddress = ducAddress;
        this.transferId = transferId;
        this.isSuccess = isSuccess;
    }

    @Override
    public String getType() {
        return "transfered";
    }
}
