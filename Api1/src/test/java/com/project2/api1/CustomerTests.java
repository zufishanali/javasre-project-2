package com.project2.api1;

import com.project2.api1.Models.Customer;
import com.project2.api1.Services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTests {
    @Autowired
    CustomerService customerService;


    @Test
    public void doesGetCustomerWork()
    {
        Assertions.assertNotNull(customerService.getCustomer(1));
        Assertions.assertNull(customerService.getCustomer(122));

    }

    @Test
    public void doesGetAllCustomersWork()
    {
        Assertions.assertNotNull(customerService.getAll());
    }

    @Test
    public void doesLoginWork()
    {
        Assertions.assertTrue(customerService.login("password", 1));
        Assertions.assertTrue(customerService.getCustomer(1).isLoggedIn());
        Assertions.assertTrue(customerService.logout(1));
        Assertions.assertFalse(customerService.getCustomer(1).isLoggedIn());
        Assertions.assertFalse(customerService.login("password1",1));
        Assertions.assertFalse(customerService.login("password",444));
        Assertions.assertFalse(customerService.logout(122));

    }
}
