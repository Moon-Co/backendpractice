package org.prgrms.kdt.order;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);
    public static void main(String[] args) throws IOException {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        applicationContext.register(AppConfiguration.class);
//        var environment = applicationContext.getEnvironment();
//        environment.setActiveProfiles("dev");
//        applicationContext.refresh();

//        var version =  environment.getProperty("kdt.version");
//        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount",Integer.class);
//        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("kdt.description", List.class);
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        //var resource = applicationContext.getResource("application.yaml");
        logger.info("logger name=>{}{}",logger.getName(),2);
        logger.info("version->{}",orderProperties.getVersion());
        logger.info("minimumOrderAmount->{}", orderProperties.getMinimumOrderAmount());
        logger.info("supportVendors->{}", orderProperties.getSupportVendors());
        logger.info("description->{}",orderProperties.getDescription());
        var customerId  = UUID.randomUUID();
        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),10L));

        System.out.println(MessageFormat.format("isJdbcRepo->{0}",voucherRepository instanceof JdbcVoucherRepository));
        System.out.println(MessageFormat.format("isJdbcRepo->{0}",voucherRepository.getClass().getCanonicalName()));


        var orderService = applicationContext.getBean(OrderService.class);
        var order= orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }},voucher.getVoucherId());


        //아래와 같이 hard coding되어있으면, FixedAmountVoucher의 로직이 변하면
        //Order도 바꿔줘야함.

        Assert.isTrue(order.totalAmount()==90L, MessageFormat.format("totalAmount {0}", order.totalAmount()));

        applicationContext.close();
    }
}
