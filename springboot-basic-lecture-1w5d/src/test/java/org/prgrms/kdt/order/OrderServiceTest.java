package org.prgrms.kdt.order;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    class OrderRepositoryStub implements OrderRepository{

        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("오더가 생성되어야한다(stub)")
    void createOrder() {
        //GIVEN (주어진 상황을 표현!)
        //Stub구현하기
        var voucherRepository = new MemoryVoucherRepository();
        var fixedAmountVoucher= new FixedAmountVoucher(UUID.randomUUID(),100);
        voucherRepository.insert(fixedAmountVoucher);

        var sut = new OrderService(new VoucherService(voucherRepository),new OrderRepositoryStub());
//orderService는 VoucherService와 OrderRepository에 의존관계가 있다.
        //When
        var order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(),200,1)),fixedAmountVoucher.getVoucherId());

        //Then(상태에 집중)
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(),is(false));
        assertThat(order.getVoucher().get().getVoucherId(),is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(),is(OrderStatus.ACCEPTED));

    }
    @Test
    @DisplayName("Mock으로 오더 생성")
    void createOrderByMock(){
        //Given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        var fixedAmountVoucher= new FixedAmountVoucher(UUID.randomUUID(),100);
        //when을 이용해서
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        var sut = new OrderService(voucherServiceMock,orderRepositoryMock);

        //When
        var order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(),
                        200,1)),
                fixedAmountVoucher.getVoucherId());

        //Then
        //행위가 일어났는지 확인(바우처서비스Mock객체와 orderRepository Mock객체에 대해서
        //어떤 메소드가 정상적으로 호출되었는가를 verify
        var inOrder = inOrder(voucherServiceMock);
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        //getvoucher가 fixedamountvoucher의 getVoucher로 실행되었는가!
        inOrder.verify(orderRepositoryMock).insert(order);
        //insert에 order가 실제로 잘 들어갔는가?
        verify(voucherServiceMock).useVoucher(fixedAmountVoucher);
        //fixedAmountVoucher가 사용되었는가?




    }
}
