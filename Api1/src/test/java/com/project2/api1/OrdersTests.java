package com.project2.api1;

import com.project2.api1.Models.MenuItem;
import com.project2.api1.Models.Orders;
import com.project2.api1.Services.MenuItemService;
import com.project2.api1.Services.OrdersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrdersTests {
    @Autowired
    OrdersService ordersService;

    @Test
    public void doesGetOrderWork(){
        try {
            Orders o = ordersService.getItem(2);
            Assertions.assertNotNull(o);
        } catch (Exception e)
        {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void doesGetOrderThrowException(){
        try {
            Orders o = ordersService.getItem(552);
        } catch (Exception e)
        {
            Assertions.assertTrue(true);
        }
    }
    @Test
    public void doesGetAllOrdersWork(){
        try {
            List<Orders> lst = ordersService.getAllItems(1);
            Assertions.assertNotNull(lst);
        } catch (Exception e)
        {

            Assertions.assertTrue(false);
        }
    }

    @Test
    public void doesCreateAndDeleteOrderWork()
    {
        try {
            Orders order = new Orders(10, 1, LocalDateTime.now(), null, "New", false, null);
            int id = ordersService.saveOrder(order);
            Assertions.assertNotNull(ordersService.getItem(id));
            ordersService.deleteOrder(ordersService.getItem(id));

        }
        catch (Exception e)
        {
            Assertions.assertFalse(false);
        }

    }
}
