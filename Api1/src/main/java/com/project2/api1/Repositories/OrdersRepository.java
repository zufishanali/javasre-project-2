package com.project2.api1.Repositories;

import com.project2.api1.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    public List<Orders> findAllByCustomerId(int id);
}
