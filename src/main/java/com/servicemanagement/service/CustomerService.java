package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;

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
     * This method should be able to create a new customer.
     *
     * @param customer
     * @param user
     * @return
     */
    Customer createNewCustomer(CustomerDTO customer, User user);

    /**
     * This method should update the data of a user.
     *
     * @param customer
     */
    void updateCustomer(CustomerDTO customer);

    /**
     * This method should delete a customer by his id.
     *
     * @param id
     */
    void deleteCustomerById(Long id);
}
