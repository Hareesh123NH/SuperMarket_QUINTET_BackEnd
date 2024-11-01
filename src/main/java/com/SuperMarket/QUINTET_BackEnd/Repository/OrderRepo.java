package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByuserId(long userId);
}
