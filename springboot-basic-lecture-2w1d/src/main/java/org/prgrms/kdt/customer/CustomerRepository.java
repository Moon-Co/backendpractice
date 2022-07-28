package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Customer update(Customer customer);

    //Customer save(Customer customer);<- 위에 두개를 묶어서 이렇게 쓰기도 한다.

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);
    //찾으면 Customer반환, 못찾으면? NULL 반환하기때문에 Null반환할거면 Optional쓰기로했었음
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);

    void deleteAll();
}
