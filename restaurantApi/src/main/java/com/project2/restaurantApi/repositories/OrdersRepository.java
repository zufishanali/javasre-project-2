package com.project2.restaurantApi.repositories;


import com.project2.restaurantApi.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByStatusNotLikeIgnoreCase(String status);

    List<Orders> findAllByFulfilledOnBetween(LocalDateTime timeStart, LocalDateTime timeEnd);


}
