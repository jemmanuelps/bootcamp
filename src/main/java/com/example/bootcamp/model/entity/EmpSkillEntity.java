package com.example.bootcamp.model.entity;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.bootcamp.model.key.EmpSkillKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value="emp_skill")
@ToString
public class EmpSkillEntity {
    @PrimaryKey
    EmpSkillKey empSkillKey;
}
