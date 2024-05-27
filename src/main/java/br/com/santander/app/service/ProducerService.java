package br.com.santander.app.service;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Producer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProducerService implements br.com.santander.domain.usecase.CallProducerApiUseCase {

    @Override
    public ResponseEntity<Producer> execute(DeviceContracted device) {

        Producer producer = new Producer();
            producer.setDevice(device.getDevice());
            producer.setReleased(true);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity("http://localhost:8080/device", producer, Producer.class);
    }

}