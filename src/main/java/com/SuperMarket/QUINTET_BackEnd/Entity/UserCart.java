package com.SuperMarket.QUINTET_BackEnd.Entity;

import jakarta.persistence.*;

@Entity
public class UserCart {

    @Id
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserCart() {
        super();
    }

    public UserCart(int id, String name, User user) {
        this.name = name;
        this.user = user;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
