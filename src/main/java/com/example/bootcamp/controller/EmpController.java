package com.example.bootcamp.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bootcamp.kafka.entity.EntityEventConsumer;
import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.util.Utils;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/employee")
public class EmpController {

    public static final String ENTITY_ACTION_CREATED = "entity-action-creation";

    @Autowired
    EntityEventConsumer entityEventConsumer;

    @PostMapping("/create-employee")
    public Mono<ResponseEntity<CreateEmpResponse>> createEmployee(@RequestBody CreateEmpRequest input) {
        Utils.loggerInfo("createEmployee", "controller", input);
        return entityEventConsumer.onEntityEvent(ENTITY_ACTION_CREATED, input)
            .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
