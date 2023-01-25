package com.example.bootcamp.kafka.entity;

import reactor.core.publisher.Mono;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.bootcamp.kafka.KafkaConstants;
import com.example.bootcamp.kafka.KafkaConsumerConfig;
import com.example.bootcamp.model.request.CreateEmpRequest;
import com.example.bootcamp.model.response.CreateEmpResponse;
import com.example.bootcamp.service.EmpService;


@KafkaListener(
        containerFactory = KafkaConsumerConfig.ECOM_ENTITY_CONTAINER_FACTORY,
        topics = KafkaConstants.KAFKA_TOPIC_ENTITY_MODIFICATION_CREATE,
        groupId = "bootcamp"
)
@ConditionalOnBean(KafkaConsumerConfig.class)
@Component
public class EntityEventConsumer {

    private final EmpService entityService;

    public static final String ENTITY_ACTION_CREATED = "entity-action-creation";

    public EntityEventConsumer(EmpService entityService) {
        this.entityService = entityService;
    }

    @KafkaHandler(isDefault = true)
    public Mono<CreateEmpResponse> onEntityEvent(@Header String eventName, @Payload CreateEmpRequest employee) {
        if (ENTITY_ACTION_CREATED.equalsIgnoreCase(eventName)) {
            return entityService.validateCreateEmployee(employee);
        }
        return entityService.validateCreateEmployee(employee);
    }

}
