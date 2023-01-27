package com.example.bootcamp.kafka.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.bootcamp.kafka.KafkaConstants;
import com.example.bootcamp.kafka.KafkaConsumerConfig;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@KafkaListener(
        containerFactory = KafkaConstants.ENTITY_CONTAINER_FACTORY,
        topics = KafkaConstants.KAFKA_APP_TOPIC,
        groupId = KafkaConstants.KAFKA_CLIENT_ID
)
@ConditionalOnBean(KafkaConsumerConfig.class)
@Component
public class EntityEventConsumer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaHandler(isDefault = true)
    public void receiveData(@Payload String employee) throws JsonMappingException, JsonProcessingException {
        if (Utils.validate(new ObjectMapper().readValue(employee, CreateEmpResponse.class))) {
            kafkaTemplate.send(KafkaConstants.KAFKA_EMPLOYEE_TOPIC, employee);
        } else {
            kafkaTemplate.send(KafkaConstants.KAFKA_DLQ_TOPIC, employee);
        }
    }
}
