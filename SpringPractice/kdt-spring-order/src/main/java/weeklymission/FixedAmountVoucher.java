package weeklymission;

import org.prgrms.kdt.voucher.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    //주어진 금액에 대해서 어떻게 discount할지에 대한 로직!
    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }
}
