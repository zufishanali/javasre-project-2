package com.project2.restaurantApi.services;


import com.project2.restaurantApi.dtos.OrderDTO;
import com.project2.restaurantApi.models.Orders;
import com.project2.restaurantApi.models.Status;
import com.project2.restaurantApi.repositories.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderService {

    final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private EmailRequestService emailRequestService;

    @Autowired
    private MessageRequestService messageRequestService;

    /**
     * This method gets all the orders that are not yet completed from db
     * @return List of all orders that are not yet completed
     */
    public List<Orders> getAllActiveOrders(){
        return ordersRepository.findByStatusNotLikeIgnoreCase("completed");
    }

    /**
     * This method gets all the details of the order specified
     * @param id
     * @return Order with the id specified
     */
    public Optional<Orders> getOrderById(int id){
        return ordersRepository.findById(id);
    }

    /**
     * this method updates the status of the order and the time it was fulfilled on and saves that to db
     * @param id
     * @param status
     * @return true if the order status and fulfilledOn date saved successfully
     */
    public boolean updateOrderStatus(int id, Status status){
        Orders order = ordersRepository.findById(id).get();
        order.setStatus(String.valueOf(status));
        order.setFulfilledOn(LocalDateTime.now());
        ordersRepository.save(order);
        // TODO: call notify customer
        logger.info("Updated order status");
        return true;
    }


    /**
     * This method returns the list of orders that were completed in the last minutes specified from the current time
     * @param minutes
     * @return List of all orders whose fulfilledOn time is between the last minutes specified and the current time
     */
    public List<Orders> getRecentCompletedOrders(int minutes){
        return ordersRepository.findAllByFulfilledOnBetween(LocalDateTime.now().minusMinutes(minutes), LocalDateTime.now());
    }

    /**
     * This method deletes the order specified from db
     * @param id
     * @return true if the order with id specified is deleted successfully
     */
    public boolean cancelOrderById(int id){
        ordersRepository.deleteById(id);
        // TODO: call notify customer
        logger.info("Deleted order");
        return true;
    }


    /**
     * This method confirms that the order is received by checking that it exists in db,
     *      updates the status and returns response to api 1
     * @param orderDto
     * @return string response whether successfully received or not
     */
    public String confirmOrder(OrderDTO orderDto){

        Optional<Orders> order = ordersRepository.findById(orderDto.getOrderId());
        if(order.isPresent()){
            updateOrderStatus(orderDto.getOrderId(), Status.CONFIRMED);
            // TODO: call notify customer
            logger.info("Order confirmed");
            return "Order " + orderDto.getOrderId() + " is confirmed and being prepared";
        } else {
            logger.warn("Order confirmation error");
            return "Issue saving order";
        }
    }

    /*
    TODO: method for notify customer through their preferred type (email, phone, or both)
     */
    /*
    public void notifyCustomer(int orderId, String contactPreference, String messageBody String customerEmail, ... String customerNumber){
        if(contactPreference.equalsIgnoreCase("both")){
            emailRequestService.sendEmail()
            messageRequestService.
        }
    }
    */
}
