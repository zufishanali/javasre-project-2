package com.project2.restaurantApi.services;


import com.project2.restaurantApi.dtos.OrderDTO;
import com.project2.restaurantApi.controllers.exceptions.InvalidStatusArgumentException;
import com.project2.restaurantApi.controllers.exceptions.OrderNotFoundException;
import com.project2.restaurantApi.models.Orders;
import com.project2.restaurantApi.models.Status;
import com.project2.restaurantApi.repositories.OrdersRepository;
import lombok.Setter;
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

    @Setter(onMethod = @__({@Autowired}) )
    private OrdersRepository ordersRepository;

    @Setter(onMethod = @__({@Autowired}) )
    private EmailRequestService emailRequestService;

    @Setter(onMethod = @__({@Autowired}) )
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
    public Optional<Orders> getOrderById(int id) throws OrderNotFoundException{
        if(!ordersRepository.findById(id).isPresent())
            throw new OrderNotFoundException("Order not found");

        return ordersRepository.findById(id);
    }


    /**
     * this method updates the status of the order and the time it was fulfilled on and saves that to db
     * @param orderDTO
     * @return true if the order status and fulfilledOn date saved successfully
     */
    public boolean updateOrderStatus(OrderDTO orderDTO) throws OrderNotFoundException, InvalidStatusArgumentException {
        int id = orderDTO.getOrderId();
        Status status = orderDTO.getOrderStatus();

        if (!ordersRepository.findById(id).isPresent()) {
            throw new OrderNotFoundException("Order not found");
        } else {
            Orders order = ordersRepository.findById(id).get();

            // TODO: CHECK PAYMENT

            if(order.getStatus().equals(String.valueOf(Status.COMPLETED)))
                throw new InvalidStatusArgumentException("Order has already been completed.");

            order.setStatus(String.valueOf(status));

            if(orderDTO.getOrderStatus().equals(Status.COMPLETED))
                order.setFulfilledOn(LocalDateTime.now());

            ordersRepository.save(order);
            try {
                String message = "Order: " + id + "\n\nYour order is " + status;
                notifyCustomer(orderDTO, message);
            } catch(RuntimeException e){
                e.printStackTrace();
                logger.warn("Customer not successfully notified "+e.getMessage());
            }
            logger.info("Updated order status");
            return true;
        }
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
     * @param orderDTO
     * @return true if the order with id specified is deleted successfully
     */
    public boolean cancelOrder(OrderDTO orderDTO){
        updateOrderStatus(orderDTO);
        ordersRepository.deleteById(orderDTO.getOrderId());
        logger.info("Deleted order");
        return true;
    }


    /**
     * This method confirms that the order is received by checking that it exists in db,
     *      updates the status and returns response to api 1
     * @param orderDTO
     * @return string response whether successfully received or not
     */
    public String confirmOrder(OrderDTO orderDTO) throws OrderNotFoundException{
        Optional<Orders> order = ordersRepository.findById(orderDTO.getOrderId());
        if(order.isPresent()){
            orderDTO.setOrderStatus(Status.CONFIRMED);
            updateOrderStatus(orderDTO);
            logger.info("Order confirmed");
            return "Order " + orderDTO.getOrderId() + " is confirmed and being prepared";
        } else {
            logger.warn("Order confirmation error");
            throw new OrderNotFoundException("Order not found");
        }
    }


    /**
     * This method checks the user's notification preference and sends them a message respectively
     * @param orderDTO
     * @param messageBody
     */
    public void notifyCustomer(OrderDTO orderDTO, String messageBody){
        if(orderDTO.getContactPreference().equalsIgnoreCase("both")){
            logger.info("notifying customer through both methods");
            emailRequestService.sendEmail(orderDTO.getCustomerEmail(), messageBody);
            messageRequestService.sendSMS(orderDTO.getCustomerNumber(), messageBody);

        } else if (orderDTO.getContactPreference().equalsIgnoreCase("sms")){
            logger.info("notifying customer through SMS");
            messageRequestService.sendSMS(orderDTO.getCustomerNumber(), messageBody);

        } else {
            logger.info("notifying customer through E-mail");
            emailRequestService.sendEmail(orderDTO.getCustomerEmail(), messageBody);
        }
    }

}
