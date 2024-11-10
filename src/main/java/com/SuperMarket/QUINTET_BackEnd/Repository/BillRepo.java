package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.Bills;
import com.SuperMarket.QUINTET_BackEnd.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bills,Long> {

    @Query("SELECT b FROM Bills b JOIN b.billProducts bp WHERE " +
            "LOWER(b.customerName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(bp.pName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(bp.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Bills> searchByCustomerNameOrProductDetails(@Param("query") String query);
}
