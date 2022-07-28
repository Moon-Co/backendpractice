package org.prgrms.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import javax.sql.DataSource;
import javax.xml.crypto.Data;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.customer"}
    )
    static class Config {
        //Repository(JdbcCustomerRepository)에 있는 Datasource의 Bean을 찾을듯
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);

            return dataSource;
        }


        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    //클래스 자체를 인스턴스로 만들어버리면 static안써도 됨!
    void setup(){
        newCustomer = new Customer(UUID.randomUUID(),"newnew-user","newnewuser@gmail.com", LocalDateTime.now());
        customerJdbcRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void testHikariConnectionPool(){
        assertThat(dataSource.getClass().getName(),is("com.zaxxer.hikari.HikariDataSource"));
    }
    @Test
    @DisplayName("고객을 추가할 수 있습니까불이")
    @Order(2)
    public void testInsert() throws InterruptedException {
     customerJdbcRepository.insert(newCustomer);
        var retrieveCustomer = customerJdbcRepository.findById((newCustomer.getCustomerId()));
        assertThat(retrieveCustomer.isEmpty(), is(false));
        assertThat(retrieveCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체고객을 조회할 수 있다람쥐.")
    @Order(3)
    public void testFindAll() throws InterruptedException {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(),is(false));
    }

    @Test
    @DisplayName("이름으로ㅓ 조회할 수 있다람쥐.")
    @Order(4)
    public void testFindByName() throws InterruptedException {
        var customer = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(),is(false));

        var unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }
    @Test
    @DisplayName("이메일로 조회할 수 있다람쥐.")
    @Order(5)
    public void testFindByEmail() throws InterruptedException {
        var customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(),is(false));

        var unknown = customerJdbcRepository.findByEmail("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }



    @Test
    @DisplayName("고객을 수정할 수 있다..")
    @Order(6)
    public void testUpdate() throws InterruptedException {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        var all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all,everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(),is(false));
        assertThat(retrievedCustomer.get(),samePropertyValuesAs(newCustomer));

    }



}
