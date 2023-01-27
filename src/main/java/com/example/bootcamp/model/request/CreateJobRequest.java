package com.example.bootcamp.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateJobRequest {
    @NotNull
    private Integer jobId;
    @NotNull
    @NotEmpty
    private String jobName;
    @NotNull
    @Min(value = 0)
    private Double javaExp;
    @NotNull
    @Min(value = 0)
    private Double springExp; 
    
}
