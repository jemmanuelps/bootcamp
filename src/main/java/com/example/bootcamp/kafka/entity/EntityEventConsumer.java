package com.example.bootcamp.kafka.entity;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.bootcamp.kafka.KafkaConstants;
import com.example.bootcamp.kafka.KafkaConsumerConfig;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@KafkaListener(
        containerFactory = KafkaConstants.ENTITY_CONTAINER_FACTORY,
        topics = KafkaConstants.KAFKA_TOPIC,
        groupId = KafkaConstants.KAFKA_CLIENT_ID
)
@ConditionalOnBean(KafkaConsumerConfig.class)
@Component
@Slf4j
public class EntityEventConsumer {

    public static final String ENTITY_ACTION_CREATED = "entity-action-creation";

    @KafkaHandler(isDefault = true)
    public void receiveData(@Payload String employee) throws JsonMappingException, JsonProcessingException {
        log.info("{}", new ObjectMapper().readValue(employee, CreateEmpResponse.class));
    }

}
