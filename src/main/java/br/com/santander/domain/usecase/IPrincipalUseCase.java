package br.com.santander.domain.usecase;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Producer;
import br.com.santander.app.service.EventProducerService;
import org.springframework.http.ResponseEntity;

public interface IPrincipalUseCase {

    public void sendContractionMessage(EventProducerService service, DeviceContracted device);

    public ResponseEntity<Producer> callProducerApi(DeviceContracted device);
}