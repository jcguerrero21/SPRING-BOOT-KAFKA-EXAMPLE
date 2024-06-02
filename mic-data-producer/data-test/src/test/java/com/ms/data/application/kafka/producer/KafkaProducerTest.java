package com.ms.data.application.kafka.producer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import com.ms.data.common.core.kafka.model.HotelAvailabilitySearch;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;

@Tag("UnitTest")
@DisplayName("Kafka Producer Test")
@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

  @InjectMocks
  private KafkaProducer kafkaProducer;

  @Mock
  private KafkaTemplate<String, HotelAvailabilitySearch> kafkaTemplate;

  @Test
  @DisplayName("should send the event message to the kafka topic without any exception")
  void sendEventHotelAvailabilitySearchWithoutExceptionTest() {
    // Given
    final HotelAvailabilitySearch hotelAvailabilitySearch = mock(HotelAvailabilitySearch.class);
    final String searchId = "testSearchId";
    final SendResult<String, HotelAvailabilitySearch> sendResult = mock(SendResult.class);
    final RecordMetadata recordMetadata = mock(RecordMetadata.class);
    final CompletableFuture<SendResult<String, HotelAvailabilitySearch>> future = CompletableFuture
        .completedFuture(sendResult);

    ReflectionTestUtils.setField(this.kafkaProducer, "topicName", "hotel_availability_searches");

    // When
    when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
    when(recordMetadata.offset()).thenReturn(123L);
    when(this.kafkaTemplate.send(anyString(), anyString(), any())).thenReturn(future);

    // Then
    this.kafkaProducer.sendEventHotelAvailabilitySearch(hotelAvailabilitySearch, searchId);
    verify(this.kafkaTemplate, times(1)).send(anyString(), anyString(), any());
    future.whenComplete((result, exception) -> {
      assertNull(exception);
    });
  }

  @Test
  @DisplayName("should throw an exception when sending the event message to the kafka topic")
  void sendEventHotelAvailabilitySearchWithExceptionTest() {
    // Given
    final HotelAvailabilitySearch hotelAvailabilitySearch = mock(HotelAvailabilitySearch.class);
    final String searchId = "testSearchId";
    final CompletableFuture<SendResult<String, HotelAvailabilitySearch>> future = new CompletableFuture<>();

    future.completeExceptionally(new RuntimeException("Test exception"));

    ReflectionTestUtils.setField(this.kafkaProducer, "topicName", "hotel_availability_searches");

    // When
    when(this.kafkaTemplate.send(anyString(), anyString(), any())).thenReturn(future);

    // Then
    assertDoesNotThrow(
        () -> this.kafkaProducer.sendEventHotelAvailabilitySearch(hotelAvailabilitySearch, searchId));
    verify(this.kafkaTemplate, times(1)).send(anyString(), anyString(), any());
    future.whenComplete((result, exception) -> {
      assertEquals("Test exception", exception.getMessage());
    });
  }

  @Test
  @DisplayName("should throw NullPointerException when topicName is null")
  void sendEventHotelAvailabilitySearchWithNullPointerExceptionTest() {
    // Given
    final HotelAvailabilitySearch hotelAvailabilitySearch = mock(HotelAvailabilitySearch.class);
    final String searchId = "testSearchId";

    ReflectionTestUtils.setField(this.kafkaProducer, "topicName", null);

    // Then
    final Exception exception = assertThrows(NullPointerException.class, () -> {
      this.kafkaProducer.sendEventHotelAvailabilitySearch(hotelAvailabilitySearch, searchId);
    });

    verify(this.kafkaTemplate, times(1)).send(null, searchId, hotelAvailabilitySearch);
    assertTrue(exception instanceof NullPointerException);
  }

}
