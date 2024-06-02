package com.ms.data.config.exception;

import com.ms.data.common.core.util.exception.GenericRestException;
import com.ms.data.model.dto.TestErrorCustomResponseDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

  private static final Logger log = LogManager.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(GenericRestException.class)
  protected ResponseEntity<TestErrorCustomResponseDTO> notFoundException(final GenericRestException ex) {
    log.error("error not found context: ", ex);
    final TestErrorCustomResponseDTO testErrorCustomResponseDTO = new TestErrorCustomResponseDTO()
        .message(ex.getMessage());
    return new ResponseEntity<>(testErrorCustomResponseDTO, ex.getHttpStatus());
  }

}
