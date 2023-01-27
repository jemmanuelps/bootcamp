package com.example.bootcamp.controller;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bootcamp.model.request.CreateJobRequest;
import com.example.bootcamp.model.request.JobByIdRequest;
import com.example.bootcamp.model.response.CreateJobResponse;
import com.example.bootcamp.model.response.EmployeeResponse;
import com.example.bootcamp.model.response.JobResponse;
import com.example.bootcamp.service.JobService;
import com.example.bootcamp.util.Utils;
import com.hazelcast.core.HazelcastInstance;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Controller
@Validated
@RequestMapping("/job")
public class JobController {
    
    @Autowired
    private JobService jobService;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @PostMapping("/create-job-profile")
    public Mono<ResponseEntity<CreateJobResponse>> createEmployee(@RequestBody @Valid CreateJobRequest input) {
        Utils.loggerInfo("createJob", "controller", input);
        return jobService.createJob(input)
            .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
            .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @PostMapping("/find-emp-by-job-id")
    public Mono<ResponseEntity<EmployeeResponse>> findEmpByJobId(@RequestBody @Valid JobByIdRequest input) {
        Utils.loggerInfo("findEmpByJobId", "controller", input);
        return jobService.findEmpByJobId(input)
            .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
            .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @PostMapping("/get-job-profile-from-cache")
    public Mono<ResponseEntity<JobResponse>> getJobProfileFromCache(@RequestBody @Valid JobByIdRequest input) {
        Utils.loggerInfo("getJobProfileFromCache", "controller", input);
        var value = retrieveMap().get(input + "");
        if (Objects.isNull(value)) {
            return jobService.getJobById(input)
                .map(entity -> {
                    try {
                        retrieveMap().put(input + "", entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    return new ResponseEntity<>(entity, HttpStatus.OK);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().build()));
        } else {
            return Mono.just(ResponseEntity.ok(value));
        }
    }

    private ConcurrentMap<String, JobResponse> retrieveMap() {
        return hazelcastInstance.getMap("map");
    }

}
