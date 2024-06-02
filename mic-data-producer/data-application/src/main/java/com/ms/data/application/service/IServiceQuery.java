package com.ms.data.application.service;

import com.ms.data.common.core.domain.entity.HotelSearch;

public interface IServiceQuery {

  String sendEventHotelAvailability(HotelSearch hotelSearch);

  HotelSearch findBySearchId(String searchId);

}
