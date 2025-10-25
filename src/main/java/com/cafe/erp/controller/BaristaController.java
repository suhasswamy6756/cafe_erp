package com.cafe.erp.controller;

import com.cafe.erp.entity.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.erp.entity.Baristas;
import com.cafe.erp.service.BaristasService;

@RestController

public class BaristaController {

    @Autowired
    private BaristasService baristasService;

    @PostMapping("/register")
    public Baristas registerBarista(@RequestBody Baristas barista) {
        // Registration logic here
        return baristasService.registerBarista(barista);
    }


    @PostMapping("/login")
    public String loginBarista(@RequestBody LoginRequest barista) {
        return baristasService.loginBarista(barista);
    }
}
