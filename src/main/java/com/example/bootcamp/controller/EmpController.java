package com.example.bootcamp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bootcamp.kafka.KafkaConstants;
import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.model.response.EmployeeResponse;
import com.example.bootcamp.service.EmpService;
import com.example.bootcamp.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Controller
@Validated
@RequestMapping("/employee")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/create-employee")
    public Mono<ResponseEntity<CreateEmpResponse>> createEmployee(@RequestBody @Valid CreateEmpRequest input) {
        Utils.loggerInfo("createEmployee", "controller", input);
        return empService.validateCreateEmployee(input)
            .map(entity -> {
                try {
                    kafkaTemplate.send(KafkaConstants.KAFKA_APP_TOPIC, new ObjectMapper().writeValueAsString(entity));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new ResponseEntity<>(entity, HttpStatus.OK);
            })
            .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @PostMapping("/find-emp-skillset")
    public Mono<ResponseEntity<List<EmployeeResponse>>> findEmpSkillset(@RequestBody Map<String, String> input) {
        Utils.loggerInfo("findEmpSkillset", "controller", input);
        var javaExp = input.containsKey("javaExp") ? Double.parseDouble(input.get("javaExp")) : 0;
        var springExp = input.containsKey("springExp") ? Double.parseDouble(input.get("springExp")) : 0;
        return empService.getEmployeeBySkillset(javaExp, springExp)
            .map(employees -> new ResponseEntity<>(employees, HttpStatus.OK))
            .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
