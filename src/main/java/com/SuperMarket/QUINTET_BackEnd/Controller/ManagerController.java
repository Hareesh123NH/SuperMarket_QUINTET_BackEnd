package com.SuperMarket.QUINTET_BackEnd.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @GetMapping("/name")
    public ResponseEntity<String> getAdmin(){
        return ResponseEntity.ok("WelCome Manager");
    }
}
