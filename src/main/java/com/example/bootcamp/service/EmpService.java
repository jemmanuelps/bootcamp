package com.example.bootcamp.service;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootcamp.model.entity.EmpEntity;
import com.example.bootcamp.model.entity.EmpSkillEntity;
import com.example.bootcamp.model.key.EmpSkillKey;
import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.repository.EmpRepository;
import com.example.bootcamp.repository.EmpSkillRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EmpService {
    @Autowired
    EmpRepository empRepository;

    @Autowired
    EmpSkillRepository empSkillRepository;

    public Mono<CreateEmpResponse> createEmployee(CreateEmpRequest employee) {
        var savedEmp = new AtomicReference<EmpEntity>();
        var entity = mapRequestToEmpEntity(employee);
        return empRepository.save(entity)
            .flatMap(createdEmployee -> {
                savedEmp.set(createdEmployee);
                return empSkillRepository.save(mapRequestToEmpSkillEntity(employee, createdEmployee));
            })
            .flatMap(createdSkill -> {
                return Mono.just(mapToResponseObject(savedEmp.get(), createdSkill, "Created"));
            });
    }

    public EmpEntity mapRequestToEmpEntity(CreateEmpRequest employee) {
        var mappedEntity = new EmpEntity();
        mappedEntity.setEmpId(employee.getEmpId());
        mappedEntity.setEmpName(employee.getEmpName());
        mappedEntity.setEmpCity(employee.getEmpCity());
        mappedEntity.setEmpPhone(employee.getEmpPhone());
        log.debug("{}", mappedEntity);
        return mappedEntity;
    }

    public EmpSkillEntity mapRequestToEmpSkillEntity(CreateEmpRequest request, EmpEntity entity) {
        var mappedEntity = new EmpSkillEntity();
        var key = new EmpSkillKey(entity.getEmpId(), request.getJavaExp(), request.getSpringExp());
        mappedEntity.setEmpSkillKey(key);
        return mappedEntity;
    }

    public CreateEmpResponse mapToResponseObject(EmpEntity empEntity, EmpSkillEntity empSkillEntity, String status) {
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
