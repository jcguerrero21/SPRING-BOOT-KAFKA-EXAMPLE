package com.ms.data.api.rest.controller;

import com.ms.data.api.TestApi;
import com.ms.data.api.rest.mapper.RestMapper;
import com.ms.data.application.service.impl.ServiceQuery;
import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.model.dto.TestCountResponseDTO;
import com.ms.data.model.dto.TestSearchRequestDTO;
import com.ms.data.model.dto.TestSearchResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestApi {

  private final ServiceQuery serviceQuery;

  private final RestMapper restMapper;

  public TestController(final ServiceQuery serviceQuery, final RestMapper restMapper) {
    this.serviceQuery = serviceQuery;
    this.restMapper = restMapper;
  }

  @Override
  public ResponseEntity<TestSearchResponseDTO> searchHotelAvailability(final TestSearchRequestDTO testSearchRequestDTO) {
    final HotelSearch hotelSearchDb = this.restMapper
        .testSearchRequestDTOToHotelSearch(testSearchRequestDTO);
    final String searchId = this.serviceQuery.sendEventHotelAvailability(hotelSearchDb);
    return new ResponseEntity<>(new TestSearchResponseDTO().searchId(searchId), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<TestCountResponseDTO> getCountOfSimilarSearches(final String searchId) {
    final HotelSearch hotelSearchDb = this.serviceQuery.findBySearchId(searchId);
    final TestCountResponseDTO testCountResponseDTO = this.restMapper
        .hotelSearchToTestCountResponseDTO(hotelSearchDb);
    return new ResponseEntity<>(testCountResponseDTO, HttpStatus.OK);
  }

}
