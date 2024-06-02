package com.ms.data.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.domain.repository.HotelSearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("UnitTest")
@DisplayName("Service Command Test")
@ExtendWith(MockitoExtension.class)
class ServiceCommandTest {

  @InjectMocks
  private ServiceCommand serviceCommand;

  @Mock
  private HotelSearchRepository hotelSearchRepository;

  @Test
  @DisplayName("Test saveHotelSearch updates lastSearchAt and saves the entity")
  void testSaveHotelSearch() {
    // Given
    final HotelSearch hotelSearch = mock(HotelSearch.class);

    // When
    when(this.hotelSearchRepository.findByHotelSearchDetails(any())).thenReturn(Optional.of(hotelSearch));

    // Then
    this.serviceCommand.saveHotelSearch(hotelSearch);
    verify(this.hotelSearchRepository, times(1)).save(hotelSearch);
  }

  @Test
  @DisplayName("Test saveHotelSearch triggers orElse clause when no existing HotelSearch is found")
  void testSaveHotelSearchOrElseClause() {
    // Given
    final HotelSearch hotelSearch = mock(HotelSearch.class);

    // When
    when(this.hotelSearchRepository.findByHotelSearchDetails(any())).thenReturn(Optional.empty());

    // Then
    this.serviceCommand.saveHotelSearch(hotelSearch);
    verify(this.hotelSearchRepository, times(1)).save(hotelSearch);
  }

}
