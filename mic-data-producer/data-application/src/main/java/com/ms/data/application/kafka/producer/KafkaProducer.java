package com.ms.data.application.kafka.producer;

import java.util.concurrent.CompletableFuture;

import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private static final Logger log = LogManager.getLogger(KafkaProducer.class);

  @Value("${topic.name}")
  private String topicName;

  private final KafkaTemplate<String, HotelAvailabilitySearch> kafkaTemplate;

  public KafkaProducer(final KafkaTemplate<String, HotelAvailabilitySearch> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendEventHotelAvailabilitySearch(final HotelAvailabilitySearch hotelAvailabilitySearch,
      final String searchId) {
    final CompletableFuture<SendResult<String, HotelAvailabilitySearch>> future = this.kafkaTemplate
        .send(this.topicName, searchId, hotelAvailabilitySearch);
    future.whenComplete((result, exception) -> {
      if (exception == null) {
        log.info("Sent message=[ {} ] with offset=[ {} ]", hotelAvailabilitySearch,
            result.getRecordMetadata().offset());
      } else {
        log.error("Unable to send message=[ {} ] due to : {}", hotelAvailabilitySearch, exception.getMessage());
      }
    });
  }

}
