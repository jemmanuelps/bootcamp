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
public class CreateEmpResponse extends CreateEmpRequest{
    String status;

    public CreateEmpResponse(Integer empId, String empName, String empCity, String empPhone, Double javaExp,
        Double springExp, String status) {
        super(empId, empName, empCity, empPhone, javaExp, springExp);
        this.status = status;
    }
}
