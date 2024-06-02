package com.ms.data.kafka.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ms.data.common.core.domain.entity.HotelSearch;
import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import com.ms.data.mapper.ConsumerMapper;
import com.ms.data.service.impl.ServiceCommand;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

@Tag("UnitTest")
@DisplayName("Kafka Consumer Test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"topic.name = hotel_availability_searches"})
class KafkaConsumerTest {

  @InjectMocks
  private KafkaConsumer kafkaConsumer;

  @Mock
  private ServiceCommand serviceCommand;

  @Mock
  private ConsumerMapper consumerMapper;

  @Test
  @DisplayName("should call saveHotelSearch when read kafka topic")
  void shouldCallSaveHotelSearchWhenReadIsInvoked() {
    // Given
    final ConsumerRecord<String, HotelAvailabilitySearch> consumerRecord = mock(ConsumerRecord.class);
    final HotelAvailabilitySearch hotelAvailabilitySearch = mock(HotelAvailabilitySearch.class);
    final HotelSearch hotelSearch = mock(HotelSearch.class);

    // When
    when(consumerRecord.key()).thenReturn("testKey");
    when(consumerRecord.value()).thenReturn(hotelAvailabilitySearch);
    when(this.consumerMapper.hotelAvailabilitySearchToHotelSearch(any(), any())).thenReturn(hotelSearch);

    // Then
    this.kafkaConsumer.read(consumerRecord);
    verify(this.serviceCommand, times(1)).saveHotelSearch(hotelSearch);
  }

}
