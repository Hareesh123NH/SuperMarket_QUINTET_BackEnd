package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import com.SuperMarket.QUINTET_BackEnd.Entity.Product;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //    @Query("Select o from orders where o.order_status <> :orderStatus")
    List<Order> findAllByorderStatusNot(String orderStatus);

    @Query("SELECT o FROM Order o WHERE " +
            "LOWER(o.user.userProfile.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(o.product.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(o.product.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Order> searchOrdersByUsernamePName(@Param("query") String query);

}
