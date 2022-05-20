package com.project2.restaurantApi;


import com.project2.restaurantApi.controllers.exceptions.InvalidStatusArgumentException;
import com.project2.restaurantApi.controllers.exceptions.OrderNotFoundException;
import com.project2.restaurantApi.dtos.OrderDTO;
import com.project2.restaurantApi.models.MenuItem;
import com.project2.restaurantApi.models.Orders;
import com.project2.restaurantApi.repositories.OrdersRepository;
import com.project2.restaurantApi.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project2.restaurantApi.models.Status.*;
import static org.mockito.Mockito.*;


public class OrderServiceTest {

    private OrdersRepository ordersRepository;
    private OrderService orderService;
    private List<Orders> expectedList;
    private Orders expectedOrder;
    private MenuItem testMenuItem;


    @BeforeEach
    public void initBeforeTest(){
        System.out.println("Init before test");

        ordersRepository = mock(OrdersRepository.class);
        orderService = new OrderService();
        orderService.setOrdersRepository(ordersRepository);


        testMenuItem = new MenuItem(1, "burger", 10.00);
        List<MenuItem> items = new ArrayList<>();
        items.add(testMenuItem);
        expectedOrder = new Orders(1, 1, LocalDateTime.now(), LocalDateTime.now(), "PLACED", items);
        expectedList = new ArrayList<>();
        expectedList.add(expectedOrder);

    }

    @Test
    public void shouldGetAllActiveOrders(){
        when(ordersRepository.findByStatusNotLikeIgnoreCase("completed")).thenReturn(expectedList);
        List<Orders> actualList = orderService.getAllActiveOrders();
        Assertions.assertEquals(expectedList, actualList);

        expectedOrder.setStatus("COMPLETED");
        when(ordersRepository.findByStatusNotLikeIgnoreCase("completed")).thenReturn(Collections.emptyList());
        List<Orders> actualList2 = orderService.getAllActiveOrders();
        Assertions.assertTrue(actualList2.isEmpty());

    }


    @Test
    public void doesGetExisitngOrderById(){
        when(ordersRepository.findById(1)).thenReturn(Optional.ofNullable(expectedOrder));
        Optional<Orders> actualOrder = orderService.getOrderById(1);
        Assertions.assertEquals(Optional.ofNullable(expectedOrder), actualOrder);
    }


    @Test
    public void getOrderByIdShouldThrowOrderNotFoundException(){
        OrderNotFoundException ex = Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderById(2);
        });
        Assertions.assertEquals("Order not found", ex.getMessage());
    }


    @Test
    public void shouldThrowInvalidStatusArgumentException(){
        when(ordersRepository.findById(1)).thenReturn(Optional.ofNullable(expectedOrder));
        expectedOrder.setStatus("COMPLETED");
        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(1);
        orderDto.setOrderStatus(COMPLETED);

        InvalidStatusArgumentException ex = Assertions.assertThrows(InvalidStatusArgumentException.class, () -> {
            orderService.updateOrderStatus(orderDto);
        });
        Assertions.assertEquals("Order has already been completed.", ex.getMessage());
    }

    @Test
    public void shouldUpdateOrder(){
        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(1);
        orderDto.setOrderStatus(COMPLETED);

        when(ordersRepository.findById(1)).thenReturn(Optional.ofNullable(expectedOrder));
        boolean didUpdate = orderService.updateOrderStatus(orderDto);
        if(didUpdate){
            String actualStatus = expectedOrder.getStatus();
            LocalDateTime actualTime = expectedOrder.getFulfilledOn();

            Assertions.assertEquals("COMPLETED", actualStatus);
            Assertions.assertNotNull(actualTime);
        }
    }


    // test get recent completed order
    @Test
    public void shouldGetRecentCompletedOrders(){
       when(ordersRepository.findAllByFulfilledOnBetween(LocalDateTime.now().minusMinutes(15), LocalDateTime.now())).thenReturn(Collections.emptyList());
       List<Orders> testList = orderService.getRecentCompletedOrders(15);
       Assertions.assertTrue(testList.isEmpty());
    }

    // test confirm order
    @Test
    public void confirmOrderShouldThrowsOrderNotFoundException(){
        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(3);
        OrderNotFoundException ex = Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderService.confirmOrder(orderDto);
        });
        Assertions.assertEquals("Order not found", ex.getMessage());
    }

    @Test
    public void confirmOrderShouldReturnOrder(){
        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(1);

        when(ordersRepository.findById(1)).thenReturn(Optional.ofNullable(expectedOrder));
        String actual = orderService.confirmOrder(orderDto);
        Assertions.assertEquals("Order 1 is confirmed and being prepared", actual);
    }


}
