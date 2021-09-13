package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;
import com.servicemanagement.dto.CustomerNewDTO;

import java.util.List;

public interface CustomerService {

    /**
     * This method should return the list of customers by an user.
     *
     * @param user
     * @return
     */
    List<CustomerDTO> getCustomersByUser(User user);

    /**
     *
     * @param customer
     * @param user
     * @return
     */
    Customer createNewCustomer(CustomerNewDTO customer, User user);
}
