package com.example.bootcamp.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.service.EmpService;
import com.example.bootcamp.util.Utils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/employee")
public class EmpController {

    @Autowired
    EmpService empService;

    @PostMapping("/create-employee")
    public Mono<ResponseEntity<CreateEmpResponse>> createEmployee(@RequestBody CreateEmpRequest input) {
        Utils.loggerInfo("createEmployee", "controller", input);
        return empService.validateCreateEmployee(input)
            .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
            .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
