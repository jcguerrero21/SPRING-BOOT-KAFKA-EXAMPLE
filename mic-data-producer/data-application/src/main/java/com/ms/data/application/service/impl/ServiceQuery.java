package com.ms.data.application.service.impl;

import com.ms.data.application.kafka.producer.KafkaProducer;
import com.ms.data.application.mapper.ApplicationMapper;
import com.ms.data.application.service.IServiceQuery;
import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.domain.repository.HotelSearchRepository;
import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import com.ms.data.common.core.util.exception.GenericRestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceQuery implements IServiceQuery {

  private final ApplicationMapper applicationMapper;

  private final HotelSearchRepository hotelSearchRepository;

  private final KafkaProducer kafkaProducer;

  public ServiceQuery(final ApplicationMapper applicationMapper, final HotelSearchRepository hotelSearchRepository,
      final KafkaProducer kafkaProducer) {
    this.applicationMapper = applicationMapper;
    this.hotelSearchRepository = hotelSearchRepository;
    this.kafkaProducer = kafkaProducer;
  }

  @Override
  @Transactional(readOnly = true)
  public String sendEventHotelAvailability(HotelSearch hotelSearch) {
    hotelSearch = this.hotelSearchRepository.findByHotelSearchDetails(hotelSearch.getHotelSearchDetails())
        .orElse(hotelSearch.setRandomValueToSearchId());
    final String searchId = hotelSearch.getSearchId();
    final HotelAvailabilitySearch hotelAvailabilitySearchKafkaSchema = this.applicationMapper
        .hotelSearchToHotelAvailabilitySearch(hotelSearch);
    this.kafkaProducer.sendEventHotelAvailabilitySearch(hotelAvailabilitySearchKafkaSchema, searchId);
    return searchId;
  }

  @Override
  @Transactional(readOnly = true)
  public HotelSearch findBySearchId(final String searchId) {
    return this.hotelSearchRepository.findBySearchId(searchId).orElseThrow(() -> new GenericRestException()
        .withHttpStatus(HttpStatus.NOT_FOUND).withMessage("Search not found [searchId] => " + searchId));
  }

}
