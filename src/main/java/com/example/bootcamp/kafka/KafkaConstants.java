package com.example.bootcamp.kafka;

public class KafkaConstants {
    public static final String KAFKA_APP_TOPIC = "app_updates";
    public static final String KAFKA_EMPLOYEE_TOPIC = "employee_updates";
    public static final String KAFKA_DLQ_TOPIC = "employee_DLQ";
    public static final String BOOTSTRAP_SERVER = "localhost:9092";
    public static final String KAFKA_CLIENT_ID = "bootcamp";
    public static final String ENTITY_CONSUMER_FACTORY = "bootcamp.EntityEventConsumerFactory";
    public static final String ENTITY_CONTAINER_FACTORY = "bootcamp.EntityKafkaContainerListenerFactory";
    public static final String ENTITY_CONSUMER_SIMPLE_FACTORY = "bootcamp.EntitySimpleConsumerFactory";
}
