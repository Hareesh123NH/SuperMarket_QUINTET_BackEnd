package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByuserId(long userId);

    List<Order> findAllByorderStatus(String orderStatus);

    @Override
    Optional<Order> findById(Long aLong);

    Optional<Order> findByUserAndProduct(User user, Product product);
}
