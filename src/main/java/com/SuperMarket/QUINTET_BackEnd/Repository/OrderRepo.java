package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByuserId(long userId);
}
