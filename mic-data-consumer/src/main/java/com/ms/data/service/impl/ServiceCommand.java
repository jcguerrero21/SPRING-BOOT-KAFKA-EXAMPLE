package com.ms.data.service.impl;

import java.util.Date;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.domain.repository.HotelSearchRepository;
import com.ms.data.service.IServiceCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceCommand implements IServiceCommand {

  private static final Logger log = LogManager.getLogger(ServiceCommand.class);

  private final HotelSearchRepository hotelSearchRepository;

  public ServiceCommand(final HotelSearchRepository hotelSearchRepository) {
    this.hotelSearchRepository = hotelSearchRepository;
  }

  @Override
  @Transactional
  public void saveHotelSearch(HotelSearch hotelSearch) {
    hotelSearch = this.hotelSearchRepository.findByHotelSearchDetails(hotelSearch.getHotelSearchDetails())
        .orElse(hotelSearch);
    hotelSearch.setLastSearchAt(new Date());
    this.hotelSearchRepository.save(hotelSearch);
    log.info("merge hotel search with id: {}", hotelSearch.getId());
  }
}
