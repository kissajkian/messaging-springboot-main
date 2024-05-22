package br.com.santander.app.rest;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Producer;
import br.com.santander.domain.usecase.PrincipalUseCase;
import br.com.santander.domain.usecase.IPrincipalUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProducerController {

    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    public HttpStatusCode callProducerApi(DeviceContracted device){
        logger.info("Chamando API Device.");

        IPrincipalUseCase useCase = new PrincipalUseCase();

        return useCase.callProducerApi(device).getStatusCode();
    }
}