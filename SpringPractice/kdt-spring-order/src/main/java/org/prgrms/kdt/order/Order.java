package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID customerid;
    private final List<OrderItem> orderItems;
    private FixedAmountVoucher fixedAmountVoucher;
    //private PercentDiscountVoucher percentdiscountvoucher;

    private Optional<voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerid, List<OrderItem> orderItems, voucher voucher) {
        this.orderId = orderId;
        this.customerid = customerid;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }
    public Order(UUID orderid, UUID customerid, List<OrderItem> orderItems) {
        this.orderId = orderid;
        this.customerid = customerid;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    //voucher에게 계산해달라고 위임.
    public long totalAmount(){
        var beforeDiscount = orderItems.stream().map(v->v.productPrice()*v.quantity())
                .reduce(0L,Long::sum);
        if(voucher.isPresent()) {
            return voucher.get().discount(beforeDiscount);
        }
        else{
            return beforeDiscount;
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public UUID getOrderId(){
        return orderId;
    }

}
