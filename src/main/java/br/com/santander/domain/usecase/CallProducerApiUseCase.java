package br.com.santander.domain.usecase;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Producer;
import org.springframework.http.ResponseEntity;

public interface CallProducerApiUseCase {

    public ResponseEntity<Producer> execute(DeviceContracted device);

}