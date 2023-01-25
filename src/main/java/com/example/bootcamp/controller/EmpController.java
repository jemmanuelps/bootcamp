package com.example.bootcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.service.EmpService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
@RequestMapping("/employee")
public class EmpController {

    @Autowired
    EmpService empService;

    @PostMapping("/create-employee")
    public ResponseEntity<Mono<CreateEmpResponse>> createEmployee(@RequestBody CreateEmpRequest input) {
        return new ResponseEntity<Mono<CreateEmpResponse>>(empService.createEmployee(input), HttpStatus.OK);
    }
}
