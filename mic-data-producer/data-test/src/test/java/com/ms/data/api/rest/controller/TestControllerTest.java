package com.ms.data.api.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Objects;

import com.ms.data.api.rest.mapper.RestMapper;
import com.ms.data.application.service.impl.ServiceQuery;
import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.model.dto.TestCountResponseDTO;
import com.ms.data.model.dto.TestSearchRequestDTO;
import com.ms.data.model.dto.TestSearchResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("UnitTest")
@DisplayName("Test Controller Test")
@ExtendWith(MockitoExtension.class)
class TestControllerTest {

  @InjectMocks
  private TestController testController;

  @Mock
  private ServiceQuery serviceQuery;

  @Mock
  private RestMapper restMapper;

  @Test
  void searchHotelAvailabilityTest() {
    // Given
    final TestSearchRequestDTO testSearchRequestDTO = mock(TestSearchRequestDTO.class);
    final HotelSearch hotelSearch = mock(HotelSearch.class);
    final String searchId = "testSearchId";

    // When
    when(this.restMapper.testSearchRequestDTOToHotelSearch(any())).thenReturn(hotelSearch);
    when(this.serviceQuery.sendEventHotelAvailability(any())).thenReturn(searchId);

    // Then
    final ResponseEntity<TestSearchResponseDTO> response = this.testController
        .searchHotelAvailability(testSearchRequestDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(searchId, Objects.requireNonNull(response.getBody()).getSearchId());
  }

  @Test
  void getCountOfSimilarSearchesTest() {
    // Given
    final HotelSearch hotelSearch = mock(HotelSearch.class);
    final TestCountResponseDTO testCountResponseDTO = mock(TestCountResponseDTO.class);
    final String searchId = "testSearchId";

    // When
    when(this.serviceQuery.findBySearchId(any())).thenReturn(hotelSearch);
    when(this.restMapper.hotelSearchToTestCountResponseDTO(any())).thenReturn(testCountResponseDTO);

    // Then
    final ResponseEntity<TestCountResponseDTO> response = this.testController
        .getCountOfSimilarSearches(searchId);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(testCountResponseDTO, response.getBody());
  }

}
