package com.servicemanagement.repository;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findCustomersByUser(User user);
}
