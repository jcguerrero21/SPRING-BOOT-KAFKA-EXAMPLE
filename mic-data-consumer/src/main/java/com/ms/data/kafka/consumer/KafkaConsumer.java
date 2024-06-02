package com.ms.data.kafka.consumer;

import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import com.ms.data.mapper.ConsumerMapper;
import com.ms.data.service.impl.ServiceCommand;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  private static final Logger log = LogManager.getLogger(KafkaConsumer.class);

  private final ServiceCommand serviceCommand;

  private final ConsumerMapper consumerMapper;

  public KafkaConsumer(final ServiceCommand serviceCommand,
      final ConsumerMapper consumerMapper) {
    this.serviceCommand = serviceCommand;
    this.consumerMapper = consumerMapper;
  }

  @KafkaListener(topics = "${topic.name}")
  public void read(final ConsumerRecord<String, HotelAvailabilitySearch> consumerRecord) {
    final String eventSearchIdKey = consumerRecord.key();
    final HotelAvailabilitySearch hotelAvailabilitySearch = consumerRecord.value();
    log.info("Avro message received for event searchId Key : {} value : {}", eventSearchIdKey,
        hotelAvailabilitySearch.toString());
    final var hotelSearch = this.consumerMapper.hotelAvailabilitySearchToHotelSearch(hotelAvailabilitySearch,
        eventSearchIdKey);
    this.serviceCommand.saveHotelSearch(hotelSearch);
  }

}
