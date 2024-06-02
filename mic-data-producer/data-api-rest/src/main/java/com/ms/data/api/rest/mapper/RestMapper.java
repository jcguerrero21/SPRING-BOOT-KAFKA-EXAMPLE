package com.ms.data.api.rest.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.model.dto.TestCountResponseDTO;
import com.ms.data.model.dto.TestSearchRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.util.ObjectUtils;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RestMapper {

  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

  @Named("stringDateTodate")
  default Date stringDateToLocalDate(final String value) throws ParseException {
    if (ObjectUtils.isEmpty(value)) {
      return null;
    }

    return formatter.parse(value);
  }

  /**
   * Maps a {@link TestSearchRequestDTO} to an {@link HotelSearch} entity.
   *
   * @param testSearchRequestDTO File entity source
   * @return HotelSearch
   */
  @Mapping(source = "hotelId", target = "hotelSearchDetails.hotelId")
  @Mapping(source = "checkIn", target = "hotelSearchDetails.checkIn", qualifiedByName = "stringDateTodate")
  @Mapping(source = "checkOut", target = "hotelSearchDetails.checkOut", qualifiedByName = "stringDateTodate")
  @Mapping(source = "ages", target = "hotelSearchDetails.ages")
  HotelSearch testSearchRequestDTOToHotelSearch(final TestSearchRequestDTO testSearchRequestDTO);

  /**
   * Maps a {@link HotelSearch} to an {@link TestCountResponseDTO} entity.
   *
   * @param hotelSearch File entity source
   * @return TestCountResponseDTO
   */
  @Mapping(source = "hotelSearchDetails.hotelId", target = "search.hotelId")
  @Mapping(source = "hotelSearchDetails.checkIn", target = "search.checkIn")
  @Mapping(source = "hotelSearchDetails.checkOut", target = "search.checkOut")
  @Mapping(source = "hotelSearchDetails.ages", target = "search.ages")
  TestCountResponseDTO hotelSearchToTestCountResponseDTO(final HotelSearch hotelSearch);

}
