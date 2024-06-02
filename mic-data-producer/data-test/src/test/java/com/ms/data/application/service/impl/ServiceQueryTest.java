package com.ms.data.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.ms.data.application.kafka.producer.KafkaProducer;
import com.ms.data.application.mapper.ApplicationMapper;
import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.domain.entity.HotelSearchDetails;
import com.ms.data.common.core.domain.repository.HotelSearchRepository;
import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import com.ms.data.common.core.util.exception.GenericRestException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("UnitTest")
@DisplayName("Service Query Test")
@ExtendWith(MockitoExtension.class)
class ServiceQueryTest {

  @InjectMocks
  private ServiceQuery serviceQuery;

  @Mock
  private ApplicationMapper applicationMapper;

  @Mock
  private HotelSearchRepository hotelSearchRepository;

  @Mock
  private KafkaProducer kafkaProducer;

  @Test
  @DisplayName("should not enter in the orElse optional clause when findByHotelSearchDetails does not return empty")
  void sendEventHotelAvailabilityTest() {
    // Given
    final HotelSearch hotelSearch = mock(HotelSearch.class);
    final HotelSearchDetails hotelSearchDetails = mock(HotelSearchDetails.class);
    final String searchId = "testSearchId";
    final HotelAvailabilitySearch hotelAvailabilitySearch = mock(HotelAvailabilitySearch.class);

    // When
    when(hotelSearch.getSearchId()).thenReturn(searchId);
    when(hotelSearch.getHotelSearchDetails()).thenReturn(hotelSearchDetails);
    when(this.hotelSearchRepository.findByHotelSearchDetails(any())).thenReturn(Optional.of(hotelSearch));
    when(this.applicationMapper.hotelSearchToHotelAvailabilitySearch(hotelSearch)).thenReturn(hotelAvailabilitySearch);

    // Then
    final String result = this.serviceQuery.sendEventHotelAvailability(hotelSearch);
    assertEquals(searchId, result);
    verify(this.kafkaProducer, times(1)).sendEventHotelAvailabilitySearch(hotelAvailabilitySearch, searchId);
  }

  @Test
  @DisplayName("should enter in the orElse optional clause when findByHotelSearchDetails returns null")
  void sendEventHotelAvailabilityTestOrElseClause() {
    // Given
    final HotelSearch hotelSearch = mock(HotelSearch.class);
    final String searchId = "testSearchId";
    final HotelAvailabilitySearch hotelAvailabilitySearch = mock(HotelAvailabilitySearch.class);

    // When
    when(hotelSearch.getSearchId()).thenReturn(searchId);
    when(hotelSearch.setRandomValueToSearchId()).thenReturn(hotelSearch);
    when(this.hotelSearchRepository.findByHotelSearchDetails(any())).thenReturn(Optional.empty());
    when(this.applicationMapper.hotelSearchToHotelAvailabilitySearch(any())).thenReturn(hotelAvailabilitySearch);

    // Then
    final String result = this.serviceQuery.sendEventHotelAvailability(hotelSearch);
    assertEquals(searchId, result);
    verify(this.kafkaProducer, times(1)).sendEventHotelAvailabilitySearch(hotelAvailabilitySearch, searchId);
  }

  @Test
  @DisplayName("should return HotelSearch when findBySearchId is called with a valid searchId")
  void findBySearchIdValidSearchIdTest() {
    // Given
    final String searchId = "testSearchId";
    final HotelSearch hotelSearch = new HotelSearch();
    hotelSearch.setSearchId(searchId);

    // When
    when(this.hotelSearchRepository.findBySearchId(anyString())).thenReturn(Optional.of(hotelSearch));

    // Then
    final HotelSearch result = this.serviceQuery.findBySearchId(searchId);
    assertEquals(hotelSearch, result);
  }

  @Test
  @DisplayName("should throw GenericRestException when findBySearchId is called with an invalid searchId")
  void findBySearchIdInvalidSearchIdTest() {
    // Given
    final String searchId = "invalidSearchId";

    // When
    when(this.hotelSearchRepository.findBySearchId(anyString())).thenReturn(Optional.empty());

    // Then
    assertThrows(GenericRestException.class, () -> this.serviceQuery.findBySearchId(searchId));
  }

}
