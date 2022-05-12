package com.project2.api1.Services;

import com.project2.api1.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    /*
        Add Customer Service methods here
     */
}
