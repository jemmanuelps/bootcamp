package com.example.bootcamp.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.example.bootcamp.model.entity.EmpEntity;

public interface EmpRepository extends ReactiveCassandraRepository<EmpEntity, Integer>{
    
}
