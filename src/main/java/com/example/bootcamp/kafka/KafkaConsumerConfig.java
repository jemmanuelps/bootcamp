package com.example.bootcamp.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration(enforceUniqueMethods = false)
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.KAFKA_CLIENT_ID);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaConstants.BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, KafkaConstants.KAFKA_CLIENT_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return props;
    }

    @Bean(KafkaConstants.ENTITY_CONSUMER_SIMPLE_FACTORY)
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean(KafkaConstants.ENTITY_CONSUMER_FACTORY)
    public ConsumerFactory<String, Object> consumerFactory(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
        return BaseKafkaConfiguration.consumerFactory(customizers);
    }

    @Bean(KafkaConstants.ENTITY_CONTAINER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> containerFactory(
            @Qualifier(KafkaConstants.ENTITY_CONSUMER_FACTORY) ConsumerFactory<String, Object> consumerFactory) {
        return BaseKafkaConfiguration.containerFactory(consumerFactory);
    }

}
