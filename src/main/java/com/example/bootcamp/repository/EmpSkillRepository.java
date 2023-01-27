package com.example.bootcamp.repository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.example.bootcamp.model.entity.EmpSkillEntity;
import com.example.bootcamp.model.key.EmpSkillKey;

import reactor.core.publisher.Flux;

public interface EmpSkillRepository extends ReactiveCassandraRepository<EmpSkillEntity, EmpSkillKey> {
    @Query("select * from emp_skill where java_exp >= ?0 ALLOW FILTERING")
    Flux<EmpSkillEntity> findByJavaExp(final Double javaExp);

    @Query("select * from emp_skill where spring_exp >= ?0 ALLOW FILTERING")
    Flux<EmpSkillEntity> findBySpringExp(final Double springExp);
}
