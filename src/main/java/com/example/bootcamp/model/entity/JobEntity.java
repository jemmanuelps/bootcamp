package com.example.bootcamp.model.entity;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.example.bootcamp.model.key.JobKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value="job")
@ToString
public class JobEntity {

    @PrimaryKey
    JobKey jobKey;

    @Column(value="emp_name")
    String jobName;
}
