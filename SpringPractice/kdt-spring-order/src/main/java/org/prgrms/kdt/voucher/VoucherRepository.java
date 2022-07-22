package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

//보통 repository는 인터페이스로 만든다.
public interface VoucherRepository {
    //Optional : voucher가 없을수도 있다.
    Optional<voucher> findById(UUID voucherId);
    voucher insert(voucher voucher);
    
}
