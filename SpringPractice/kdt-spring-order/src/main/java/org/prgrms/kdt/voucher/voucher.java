package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface voucher{
    UUID getVoucherId();

    long discount(long beforeDiscount);

}
