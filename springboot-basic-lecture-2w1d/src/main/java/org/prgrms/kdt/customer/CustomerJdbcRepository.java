package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final RowMapper<Customer> rowMapper = (resultSet, i) -> {
    var customerName = resultSet.getString("name");
    var email = resultSet.getString("email");
    var customerId = toUUID(resultSet.getBytes("customer_id"));
    var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
            resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

    var createdAt = resultSet.getTimestamp("Created_at").toLocalDateTime();
    return new Customer(customerId, customerName, email, lastLoginAt, createdAt);

};
    //DataSource에서 Connection 가져오기
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;


    public CustomerJdbcRepository(DataSource dataSource,JdbcTemplate jdbcTemplate){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update("INSERT INTO customers(customer_id,name,email,created_at) VALUES(UUID_TO_BIN(?), ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
        customer.getName(),
        customer.getEmail(),
        Timestamp.valueOf(customer.getCreatedAt()));
        if(update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("UPDATE customers SET name = ?, email = ?, last_login_at=? WHERE customer_id= UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt()!=null?Timestamp.valueOf(customer.getLastLoginAt()):null,
                customer.getCustomerId().toString().getBytes());
        if(update != 1){
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }



    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", rowMapper);
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at")!=null?
                resultSet.getTimestamp("last_login_at").toLocalDateTime(): null;

        var createdAt = resultSet.getTimestamp("Created_at").toLocalDateTime();
        allCustomers.add(new Customer(customerId, customerName,email,lastLoginAt,createdAt));
    }


    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
        return Optional.ofNullable(jdbcTemplate.queryForObject("select*from customers WHERE customer_id = UUID_TO_BIN(?)",rowMapper,
                customerId.toString().getBytes()));
        }catch(EmptyResultDataAccessException e){
            logger.error("Got Empty Result",e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name){
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select*from customers WHERE name=?",rowMapper,name ));
        }catch(EmptyResultDataAccessException e){
            logger.error("Got Empty Result",e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select*from customers WHERE email = ?",rowMapper,
                    email));
        }catch(EmptyResultDataAccessException e){
            logger.error("Got Empty Result",e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }

    public int count(){
        return jdbcTemplate.queryForObject("select count(*) from customers",Integer.class);

    }
    static UUID toUUID(byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());

    }
}
