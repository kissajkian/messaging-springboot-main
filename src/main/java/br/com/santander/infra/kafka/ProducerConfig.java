package br.com.santander.infra.kafka;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Retry;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String boostrapAddress;

    @Value(value = "${topic.retry-devices-events}")
    private String topic;

    @Bean
    public ProducerFactory<String, Retry> deviceFactory(){
        Map<String, Object> configProps = new HashMap<>();
            configProps.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapAddress);
            configProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Retry> kafkaTemplate() {
        return new KafkaTemplate<>(deviceFactory());
    }

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder
                .name(topic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}