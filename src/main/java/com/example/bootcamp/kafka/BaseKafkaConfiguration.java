package com.example.bootcamp.kafka;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * Contains shared kafka related configuration properties and logic
 */
@Configuration
public abstract class BaseKafkaConfiguration {

    /**
     * Creates a consumer factory using the configuration's kafka properties.
     *
     * <p>Generally, child classes override this method to define specifically
     * named beans to support connecting to different kafka brokers.
     * @return a new ConsumerFactory.
     */
    public static ConsumerFactory<String, Object> consumerFactory(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
        KafkaProperties kafkaProps = new KafkaProperties();
        var config = kafkaProps.buildConsumerProperties();
        DefaultKafkaConsumerFactory<String, Object> factory = new DefaultKafkaConsumerFactory<>(config);
        customizers.orderedStream().forEachOrdered(customizer -> customizer.customize(factory));
        return factory;
    }

    /**
     * Creates a container factory using a specific consumer factory.
     *
     * <p>Generally, child classes override this method to define specifically
     * named beans to support configuring {@code KafkaListener}s that speak
     * to different kafka brokers.
     * @param consumerFactory The factory to use.
     * @return a new container factory.
     */
    public static KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> containerFactory(ConsumerFactory<String, Object> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
