package com.example.bootcamp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bootcamp.model.entity.JobEntity;
import com.example.bootcamp.model.key.JobKey;
import com.example.bootcamp.model.request.CreateJobRequest;
import com.example.bootcamp.model.request.JobByIdRequest;
import com.example.bootcamp.model.response.CreateJobResponse;
import com.example.bootcamp.model.response.EmployeeResponse;
import com.example.bootcamp.model.response.JobResponse;
import com.example.bootcamp.repository.JobRepository;
import com.example.bootcamp.util.Utils;

import reactor.core.publisher.Mono;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    EmpService empService;

    public Mono<JobResponse> getJobById(JobByIdRequest input) {
        Utils.loggerInfo("getJobById", "service", input); 
        return jobRepository.findByJobId(input.getJobId())
            .map(job -> new JobResponse(job.getJobKey().getJobId(), job.getJobName(), job.getJobKey().getJavaExp(), job.getJobKey().getSpringExp()));

    }
    
    public Mono<CreateJobResponse> createJob(CreateJobRequest job) {  
        Utils.loggerInfo("createJob", "service", job);       
        return jobRepository.existsById(new JobKey(job.getJobId(), job.getJavaExp(), job.getSpringExp()))
            .flatMap(jobAlreadyExists -> {
                if (jobAlreadyExists) {
                    Utils.loggerInfo("jobAlreadyExists", "service", jobAlreadyExists);
                    return Mono.just(new CreateJobResponse(
                        job.getJobId(),
                        job.getJobName(),
                        job.getJavaExp(),
                        job.getSpringExp(),
                        "Already exists"
                    ));
                } else {
                    var jobEntity = new JobEntity(new JobKey(job.getJobId(), job.getJavaExp(), job.getSpringExp()), job.getJobName());
                    return jobRepository.save(jobEntity)
                        .map(savedJob -> {
                            return new CreateJobResponse(
                                savedJob.getJobKey().getJobId(),
                                savedJob.getJobName(),
                                savedJob.getJobKey().getJavaExp(),
                                savedJob.getJobKey().getSpringExp(),
                                "Created"
                            );
                        });
                }
            });
    }

    public Mono<EmployeeResponse> findEmpByJobId(JobByIdRequest jobRequest) {
        Utils.loggerInfo("findEmpByJobId", "service", jobRequest); 
        return  jobRepository.findByJobId(jobRequest.getJobId())
            .flatMap(job -> empService.getEmployeeBySkillset(job.getJobKey().getJavaExp(), job.getJobKey().getSpringExp()))
            .map(employeeList -> employeeList.get(0));
    }
}
