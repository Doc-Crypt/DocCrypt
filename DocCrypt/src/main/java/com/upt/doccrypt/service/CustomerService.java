package com.upt.doccrypt.service;

import com.upt.doccrypt.model.file.Document;
import com.upt.doccrypt.model.file.Folder;
import com.upt.doccrypt.model.user.Customer;
import com.upt.doccrypt.model.user.User;

import java.util.List;

public interface CustomerService {
    Customer register(Customer customer);
    List<Customer> getAll();
    Customer findByUsername(String username);
    Boolean containUserByEmail(String email);
    Customer getCustomerByEmail(String email);

    void addFolder(Customer customer, Folder folder);
}

