package com.example.bootcamp.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateJobResponse {
    private Integer jobId;
    private String jobName;
    private Double javaExp;
    private Double springExp; 
    private String status;
}
