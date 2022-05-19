package com.project2.api1.Services;

import com.project2.api1.Models.Customer;
import com.project2.api1.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    /*
        Add Customer Service methods here
     */

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(int id){
        if (customerRepository.findById(id).isPresent())
        {
            return customerRepository.findById(id).get();
        }
        else
        {
            return null;
        }
    }


    public boolean login(String password, int customerId) {
        if (customerRepository.findById(customerId).isPresent()) {
            Customer c = customerRepository.findById(customerId).get();
            if (password.equals(c.getPassword())) {
                c.setLoggedIn(true);
                c.setSessionEnds(LocalDateTime.now().plusSeconds(180));
                customerRepository.save(c);
                return true;
            } else return false;
        } else return false;
    }

    public boolean logout(int customerId)
    {
        if (customerRepository.findById(customerId).isPresent()) {
            Customer c = customerRepository.findById(customerId).get();
            c.setLoggedIn(false);
            customerRepository.save(c);
            return true;
        } else return false;
    }

}
