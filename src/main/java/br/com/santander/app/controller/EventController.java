package br.com.santander.app.controller;

import br.com.santander.app.dto.DeviceContracted;
import br.com.santander.app.service.DeviceService;
import br.com.santander.app.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class EventController {

  private static final Logger logger = LoggerFactory.getLogger(EventController.class);

  @Autowired
  private DeviceService deviceService;

  @Autowired
  private ProducerService producerService;

  @KafkaListener(topics = "consumer.contractions.events", groupId = "group-1", containerFactory = "deviceListener")
  public void receiveMessage(DeviceContracted message) {

    logger.info("Consumindo Topic Kafka: ".concat(message.toString()));

    HttpStatusCode codeReturn = producerService.execute(message).getStatusCode();

    if (HttpStatus.CREATED.equals(codeReturn)) {

      try{
        logger.info("Persistindo dados na tabela device. Device: ".concat(message.getDevice()).concat(" ReleaseDate: ".concat(message.getReleaseDate()).concat(" Clientname: ".concat(message.getClientName()))));
        deviceService.execute(message);
      } catch (Exception e){
        System.out.println("Entrar no fluxo Retry");
      }

    }
  }
//KafkaException
}