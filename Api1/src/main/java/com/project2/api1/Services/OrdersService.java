package com.project2.api1.Services;

import com.project2.api1.Repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

        /*
        Add Orders Service methods here
     */
}
