package com.ms.data.application.mapper;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ApplicationMapper {

  /**
   * Maps a {@link HotelSearch} to an {@link HotelAvailabilitySearch} entity.
   *
   * @param hotelSearch File entity source
   * @return HotelAvailabilitySearch
   */
  @Mapping(source = "hotelSearchDetails.hotelId", target = "hotelId")
  @Mapping(source = "hotelSearchDetails.checkIn", target = "checkIn")
  @Mapping(source = "hotelSearchDetails.checkOut", target = "checkOut")
  @Mapping(source = "hotelSearchDetails.ages", target = "ages")
  HotelAvailabilitySearch hotelSearchToHotelAvailabilitySearch(HotelSearch hotelSearch);

}
