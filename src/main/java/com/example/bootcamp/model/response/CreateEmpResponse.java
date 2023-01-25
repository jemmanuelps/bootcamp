package com.example.bootcamp.model.response;

import com.example.bootcamp.model.request.CreateEmpRequest;

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
public class CreateEmpResponse {
    private Integer empId;
    private String empName;
    private String empCity;
    private String empPhone;
    private Double javaExp;
    private Double springExp; 
    private String status;
}
