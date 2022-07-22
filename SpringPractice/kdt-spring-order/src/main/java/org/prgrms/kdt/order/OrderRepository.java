package org.prgrms.kdt.order;

//영속성이 보장된다.
public interface OrderRepository {
    Order insert(Order order);
}
