package br.com.santander.app.service;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Retry;
import br.com.santander.domain.usecase.RetryUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RetryService implements RetryUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final String topic;
    private final KafkaTemplate<String, Retry> kafkaTemplate;

    public RetryService(@Value("${topic.retry-devices-events}") String topic, KafkaTemplate<String, Retry> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void execute(Retry retry) {
        logger.info("Enviando para o Topic Kafka Retry.");
        kafkaTemplate.send(topic, retry);
    }
}