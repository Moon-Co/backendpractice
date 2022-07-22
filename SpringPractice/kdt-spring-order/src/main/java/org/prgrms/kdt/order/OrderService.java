package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    //Voucher서비스와 Order에 대한 정보를 기록, 조회할 수 있는
    // Repository에 대해서 의존성을 가진다.
    private final VoucherService voucherservice;
    private final OrderRepository orderRepository;

    //orderservice 생성할 때, 이 서비스에 대한 객체를 외부에서 주입할 수 있도록.
    //->생성자를 이용
    public OrderService(VoucherService voucherservice, OrderRepository orderRepository) {
        this.voucherservice = voucherservice;
        this.orderRepository = orderRepository;
    }

    //생성에 대한 책임을 갖게 됨.
    public Order createOrder(UUID customerId, List<OrderItem> orderItems) {
        var order = new Order(UUID.randomUUID(), customerId, orderItems);
        return orderRepository.insert(order);

    }

    public Order createOrder(UUID customerId, List<OrderItem> orderItems, UUID voucherId) {
        var voucher = voucherservice.getVoucher(voucherId);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
        orderRepository.insert(order);
        voucherservice.useVoucher(voucher);
        return order;

    }
}
