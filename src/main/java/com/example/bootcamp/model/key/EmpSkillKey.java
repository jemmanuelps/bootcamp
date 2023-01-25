package com.example.bootcamp.model.key;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class EmpSkillKey implements Serializable {
    @PrimaryKeyColumn(name = "emp_id", ordinal = 0,
            type = PrimaryKeyType.PARTITIONED) 
    Integer empId;

    @PrimaryKeyColumn(name = "java_exp", ordinal = 1,
            type = PrimaryKeyType.CLUSTERED) 
    Double javaExp;

    @PrimaryKeyColumn(name = "spring_exp", ordinal = 2,
            type = PrimaryKeyType.CLUSTERED) 
    Double springExp;
}
