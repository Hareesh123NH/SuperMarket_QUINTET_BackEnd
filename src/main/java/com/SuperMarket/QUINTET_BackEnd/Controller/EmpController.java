package com.SuperMarket.QUINTET_BackEnd.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmpController {

    @GetMapping("/name")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("WelCome Employee");
    }
}
