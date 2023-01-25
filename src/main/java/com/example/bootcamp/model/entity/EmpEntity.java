package com.example.bootcamp.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value="emp")
@ToString
public class EmpEntity {
    @PrimaryKey(value = "emp_id") 
    Integer empId;
    @Column(value="emp_name")
    String empName;
    @Column(value="emp_city")
    String empCity;
    @Column(value="emp_phone")
    String empPhone;

}
