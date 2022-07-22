package org.prgrms.kdt.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements voucher {
     public final UUID voucherId;
     private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }
//특정한 비율만큼 할인하게.
    @Override

    public long discount(long beforeDiscount) {
        return beforeDiscount*(percent/10);
    }
}
