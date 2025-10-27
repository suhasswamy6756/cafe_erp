package com.cafe.erp.auth.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.auth.entity.Baristas;
import com.cafe.erp.auth.entity.LoginRequest;
import com.cafe.erp.auth.entity.LoginResponse;
import com.cafe.erp.auth.service.BaristasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class BaristaController {

    @Autowired
    private BaristasService baristasService;

    @GetMapping("/baristas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Baristas>>> getAllBaristas() {
        return ResponseEntity.ok(ApiResponse.success("Baristas fetched successfully", baristasService.getAllBaristas(), 200));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Baristas>> registerBarista(@RequestBody Baristas barista) {
        // Registration logic here
//        return baristasService.registerBarista(barista);
        return ResponseEntity.ok(ApiResponse.success("Barista registered successfully", baristasService.registerBarista(barista), 201));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginBarista(@RequestBody LoginRequest barista) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", baristasService.loginBarista(barista), 200));
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

        return ResponseEntity.ok(ApiResponse.success("Logout successful", token, 200));
    }


}
