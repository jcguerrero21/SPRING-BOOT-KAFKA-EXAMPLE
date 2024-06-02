package com.ms.data.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.util.ObjectUtils;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ConsumerMapper {

  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

  @Named("charSequenceToString")
  default String charSequenceToString(final CharSequence value) {
    if (ObjectUtils.isEmpty(value)) {
      return null;
    }

    return value.toString();
  }

  @Named("charSequenceDateToDate")
  default Date charSequenceDateToDate(final CharSequence value) throws ParseException {
    if (ObjectUtils.isEmpty(value)) {
      return null;
    }

    return formatter.parse(value.toString());
  }

  /**
   * Maps a {@link HotelAvailabilitySearch} to an {@link HotelSearch} entity.
   *
   * @param hotelAvailabilitySearch File entity source
   * @return HotelSearch
   */
  @Mapping(source = "eventSearchIdKey", target = "searchId")
  @Mapping(source = "hotelAvailabilitySearch.hotelId", target = "hotelSearchDetails.hotelId", qualifiedByName = "charSequenceToString")
  @Mapping(source = "hotelAvailabilitySearch.checkIn", target = "hotelSearchDetails.checkIn", qualifiedByName = "charSequenceDateToDate")
  @Mapping(source = "hotelAvailabilitySearch.checkOut", target = "hotelSearchDetails.checkOut", qualifiedByName = "charSequenceDateToDate")
  @Mapping(source = "hotelAvailabilitySearch.ages", target = "hotelSearchDetails.ages")
  HotelSearch hotelAvailabilitySearchToHotelSearch(HotelAvailabilitySearch hotelAvailabilitySearch,
      String eventSearchIdKey);

}
