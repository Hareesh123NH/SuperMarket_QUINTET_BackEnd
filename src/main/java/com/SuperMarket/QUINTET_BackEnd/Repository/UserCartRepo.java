package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCartRepo extends JpaRepository<UserCart,Integer> {
}
