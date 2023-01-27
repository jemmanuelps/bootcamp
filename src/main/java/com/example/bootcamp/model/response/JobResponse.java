package com.example.bootcamp.model.response;

import java.io.Serializable;

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
public class JobResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer jobId;
    private String jobName;
    private Double javaExp;
    private Double springExp; 
}
