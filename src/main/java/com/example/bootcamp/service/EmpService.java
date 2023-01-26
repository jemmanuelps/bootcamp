package com.example.bootcamp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootcamp.model.entity.EmpEntity;
import com.example.bootcamp.model.entity.EmpSkillEntity;
import com.example.bootcamp.model.key.EmpSkillKey;
import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.repository.EmpRepository;
import com.example.bootcamp.repository.EmpSkillRepository;
import com.example.bootcamp.util.Utils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmpService {
    @Autowired
    EmpRepository empRepository;

    @Autowired
    EmpSkillRepository empSkillRepository;

    public Mono<List<CreateEmpResponse>> getEmployeeBySkillset(Double javaExp, Double springExp) {
        Utils.loggerInfo("getEmployeeBySkillset", "service", javaExp, springExp);
        
        return empSkillRepository.findByJavaExp(javaExp)
                .map(skillSet -> {
                    var response = new CreateEmpResponse();
                    response.setEmpId(skillSet.getEmpSkillKey().getEmpId());
                    response.setJavaExp(skillSet.getEmpSkillKey().getJavaExp());
                    response.setSpringExp(skillSet.getEmpSkillKey().getSpringExp());
                    return response;
                })
                .map(empResponse -> {
                    return empRepository.findById(empResponse.getEmpId())
                        .map(employee -> {
                            empResponse.setEmpName(employee.getEmpName());
                            empResponse.setEmpCity(employee.getEmpCity());
                            empResponse.setEmpPhone(employee.getEmpPhone());
                            return empResponse;
                        }).flux();
                })
                .flatMap(response -> response)
                .collectList();
    }

    public Mono<CreateEmpResponse> validateCreateEmployee(CreateEmpRequest employee) {  
        Utils.loggerInfo("validateCreateEmployee", "service", employee);       
        return employeeExists(employee.getEmpId())
            .flatMap(employeeAlreadyExists -> {
                if (employeeAlreadyExists) {
                    Utils.loggerInfo("entityAlreadyExists", "service", employee);
                    return Mono.just(new CreateEmpResponse(
                        employee.getEmpId(),
                        employee.getEmpName(),
                        employee.getEmpCity(),
                        employee.getEmpPhone(),
                        employee.getJavaExp(),
                        employee.getSpringExp(),
                        "Already exists"
                    ));
                } else {
                    return createEmployeeAndSkill(employee);
                }
            });
    }

    private Mono<Boolean> employeeExists(Integer empId) {
        Utils.loggerInfo("employeeExists", "service", empId);
        return empRepository.findById(empId)
            .map(entity -> true)
            .switchIfEmpty(Mono.just(false));  
    }

    private Mono<CreateEmpResponse> createEmployeeAndSkill(CreateEmpRequest employee) {
        Utils.loggerInfo("createEmployeeAndSkill", "service", employee);  
        var savedEmp = new AtomicReference<EmpEntity>();
        var entity = mapRequestToEmpEntity(employee);
        return empRepository.save(entity)
            .flatMap(createdEmployee -> {
                savedEmp.set(createdEmployee);
                return empSkillRepository.save(mapRequestToEmpSkillEntity(employee, createdEmployee))
                    .flatMap(createdSkill -> {
                        return Mono.just(mapToResponseObject(savedEmp.get(), createdSkill, "Created"));
                    });
            });
    }

    private EmpEntity mapRequestToEmpEntity(CreateEmpRequest employee) {
        Utils.loggerInfo("mapRequestToEmpEntity", "service", employee); 
        var mappedEntity = new EmpEntity();
        mappedEntity.setEmpId(employee.getEmpId());
        mappedEntity.setEmpName(employee.getEmpName());
        mappedEntity.setEmpCity(employee.getEmpCity());
        mappedEntity.setEmpPhone(employee.getEmpPhone());
        return mappedEntity;
    }

    private EmpSkillEntity mapRequestToEmpSkillEntity(CreateEmpRequest request, EmpEntity entity) {
        Utils.loggerInfo("mapRequestToEmpSkillEntity", "service", request, entity); 
        var mappedEntity = new EmpSkillEntity();
        var key = new EmpSkillKey(entity.getEmpId(), request.getJavaExp(), request.getSpringExp());
        mappedEntity.setEmpSkillKey(key);
        return mappedEntity;
    }

    private CreateEmpResponse mapToResponseObject(EmpEntity empEntity, EmpSkillEntity empSkillEntity, String status) {
        Utils.loggerInfo("mapRequestToEmpSkillEntity", "service", empEntity, empSkillEntity, status); 
        return new CreateEmpResponse(
            empEntity.getEmpId(),
            empEntity.getEmpName(),
            empEntity.getEmpCity(),
            empEntity.getEmpPhone(),
            empSkillEntity.getEmpSkillKey().getJavaExp(),
            empSkillEntity.getEmpSkillKey().getSpringExp(),
            status
        );
    }
}
