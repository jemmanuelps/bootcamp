package com.example.bootcamp.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class CreateEmpRequest {
    @NotNull
    private Integer empId;
    @NotNull
    @NotEmpty
    private String empName;
    @NotNull
    @NotEmpty
    private String empCity;
    @NotNull
    @NotEmpty
    private String empPhone;
    @NotNull
    @Min(value = 0)
    private Double javaExp;
    @NotNull
    @Min(value = 0)
    private Double springExp; 
}
