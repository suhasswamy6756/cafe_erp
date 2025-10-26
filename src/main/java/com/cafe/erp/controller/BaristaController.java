package com.cafe.erp.controller;

import com.cafe.erp.entity.ApiResponse;
import com.cafe.erp.entity.Baristas;
import com.cafe.erp.entity.LoginRequest;
import com.cafe.erp.entity.LoginResponse;
import com.cafe.erp.service.BaristasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BaristaController {

    @Autowired
    private BaristasService baristasService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Baristas>> registerBarista(@RequestBody Baristas barista) {
        // Registration logic here
//        return baristasService.registerBarista(barista);
        return ResponseEntity.ok(ApiResponse.success("Barista registered successfully", baristasService.registerBarista(barista)));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginBarista(@RequestBody LoginRequest barista) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", baristasService.loginBarista(barista)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logoutBarista(
            @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400 ,"Invalid Authorization header" ));
        }

        String token = authorizationHeader.replace("Bearer ", "").trim();

        baristasService.logoutBarista(token);

        return ResponseEntity.ok(ApiResponse.success("Logout successful", token));
    }


}
