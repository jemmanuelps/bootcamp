package com.example.bootcamp.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.example.bootcamp.model.entity.EmpSkillEntity;
import com.example.bootcamp.model.key.EmpSkillKey;

public interface EmpSkillRepository extends ReactiveCassandraRepository<EmpSkillEntity, EmpSkillKey>{
    
}
