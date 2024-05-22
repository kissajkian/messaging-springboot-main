package br.com.santander.domain.usecase;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Producer;
import br.com.santander.app.service.EventProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PrincipalUseCase implements IPrincipalUseCase {

    @Override
    public void sendContractionMessage(EventProducerService service, DeviceContracted device) {
        service.sendMessage(device);
    }

    @Override
    public ResponseEntity<Producer> callProducerApi(DeviceContracted device) {

        Producer producer = new Producer();
            producer.setDevice(device.getDevice());
            producer.setReleased(true);

        RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Producer> resp = restTemplate.
                    postForEntity("http://localhost:8080/device", producer, Producer.class);

        return resp;
    }
}