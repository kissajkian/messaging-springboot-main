package br.com.santander.app.service;

import br.com.santander.app.rest.ProducerController;
import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Retry;
import br.com.santander.infra.db.model.Devices;
import br.com.santander.infra.dataprovider.DevicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
public class EventConsumerService {

  private static final Logger logger = LoggerFactory.getLogger(EventConsumerService.class);

  @Autowired
  private ProducerController producerController;

  @Autowired
  private DevicesService devicesService;

  @KafkaListener(topics = "consumer.contractions.events", groupId = "group-1", containerFactory = "deviceListener")
  public void receiveMessage(DeviceContracted message) {
    logger.info("Consumindo Topic Kafka: ".concat(message.toString()));
    HttpStatusCode codeReturn = producerController.callProducerApi(message);

    if(HttpStatus.CREATED.equals(codeReturn)){
      try {
        logger.info("Persistindo dados na tabela device. Device: ".concat(message.getDevice()).concat(" ReleaseDate: ".concat(message.getReleaseDate()).concat(" Clientname: ".concat(message.getClientName()))));
        devicesService.saveDetails(getDevices(message));
      } catch (Exception e){
        // @TODO: enviar para o topic retry
      }

    } else {
      // @TODO: enviar para o topic retry
        // @TODO: se houver erro, enviar para o topic dlq
    }
  }

  private Devices getDevices(DeviceContracted dto){
    Devices devices = new Devices();
      devices.setDevice(dto.getDevice());
      devices.setReleasedate(Date.valueOf(dto.getReleaseDate()));
      devices.setClientname(dto.getClientName());
    return devices;
  }

  private Retry retryConfig(DeviceContracted device, Exception e){
    Retry retry = new Retry();
      retry.setDevice(device.getDevice());
      retry.setErrorEnum(e.getCause().getMessage());
      retry.setErrorException(e.getMessage());
    return retry;
  }
}