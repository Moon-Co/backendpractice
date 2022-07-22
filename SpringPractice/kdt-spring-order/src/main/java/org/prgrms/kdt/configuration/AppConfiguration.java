package org.prgrms.kdt.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.order","org.prgrms.kdt.voucher","org.prgrms.kdt.configuration"})
//VoucherService, OrderService, VoucherRepo, OrderRepo 생성에 대한 책임을 가짐.
//각각의 Repo, Service간의 의존, 연결 관계를 담당ㅇ.
@PropertySource(value="application.yaml",factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

}


