package com.example.finalproject.controller;

import com.example.finalproject.dto.UserRouteResponseDto;
import com.example.finalproject.dto.UserRouteRequestDto;
import com.example.finalproject.service.user.UserRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRouteController {

    @Autowired
    private UserRouteService userRouteService;

    @PostMapping("/src/users/findRoute")
    public UserRouteResponseDto findRoute(@RequestBody UserRouteRequestDto userRouteRequestDto) {
        return userRouteService.findRoute(userRouteRequestDto);
    }

}
