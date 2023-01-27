package com.example.bootcamp.repository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.example.bootcamp.model.entity.JobEntity;
import com.example.bootcamp.model.key.JobKey;

import reactor.core.publisher.Mono;

public interface JobRepository extends ReactiveCassandraRepository<JobEntity, JobKey>{
    @Query("select * from job where job_id >= ?0 ALLOW FILTERING")
    Mono<JobEntity> findByJobId(final Integer jobId);
}
