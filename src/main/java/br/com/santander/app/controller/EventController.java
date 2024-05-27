package br.com.santander.app.controller;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.dto.Retry;
import br.com.santander.app.service.DeviceService;
import br.com.santander.app.service.ProducerService;
import br.com.santander.app.service.RetryService;
import br.com.santander.cross.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.util.concurrent.ExecutionException;


@Service
public class EventController {

  private static final Logger logger = LoggerFactory.getLogger(EventController.class);

  @Autowired
  private DeviceService deviceService;

  @Autowired
  private ProducerService producerService;

  @Autowired
  private RetryService retryService;

  @KafkaListener(topics = "consumer.contractions.events", groupId = "group-1", containerFactory = "deviceListener")
  public void receiveMessage(DeviceContracted message) {

    logger.info("Consumindo Topic Kafka: ".concat(message.toString()));

    HttpStatusCode codeReturn = producerService.execute(message).getStatusCode();

    if (HttpStatus.CREATED.equals(codeReturn)) {

      try{
        logger.info("Persistindo dados na tabela device. Device: ".concat(message.getDevice()).concat(" ReleaseDate: ".concat(message.getReleaseDate()).concat(" Clientname: ".concat(message.getClientName()))));
        deviceService.execute(message);
      } catch (JpaSystemException e){
        retryService.execute(configureRetry(message, Constants.DATABASE_ERROR, e.getMessage()));
      }

    }
  }

  private Retry configureRetry(DeviceContracted deviceContracted, String errorNum, String error){
    Retry retry = new Retry();
      retry.setDevice(deviceContracted.getDevice());
      retry.setErrorEnum(errorNum);
      retry.setErrorException(error);
    return retry;
  }
}