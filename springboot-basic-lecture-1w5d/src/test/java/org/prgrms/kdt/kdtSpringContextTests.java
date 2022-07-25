package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.voucher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
//ApplicationContext를 만들어주는놈

@SpringJUnitConfig
@Qualifier("memory")
public class kdtSpringContextTests {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order","org.prgrms.kdt.configuration"}
    )
    //ComponentScan: 저기 안에 넣으면 , 각각의 서비스와 레포지토리가 bean으로 등록된다.
    static class Config{
    }

    @Autowired
    OrderService orderService;

    @Autowired
    ApplicationContext context;


    @Autowired @Qualifier("memory")
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext 만들어져야함")
    public void testApplicationContext(){
        assertThat(context,notNullValue());

    }

    @Test
    @DisplayName("VoucherRepository가 bean으로 등록되어있어야해")
    public void testVoucherRepositoryCreation(){
        var bean = context.getBean(MemoryVoucherRepository.class);
        assertThat(bean,notNullValue());

    }
    @Test
    @DisplayName("OrderService를 사용해서 주문을 생성할 수 있다.")
    public void testOrderService(){
        var fixedAmountVoucher= new FixedAmountVoucher(UUID.randomUUID(),100);
        voucherRepository.insert(fixedAmountVoucher);

//orderService는 VoucherService와 OrderRepository에 의존관계가 있다.
        //When
        var order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(),
                        200,1)),
                fixedAmountVoucher.getVoucherId());

        //Then(상태에 집중)
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(),is(false));
        assertThat(order.getVoucher().get().getVoucherId(),is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(),is(OrderStatus.ACCEPTED));


    }
}