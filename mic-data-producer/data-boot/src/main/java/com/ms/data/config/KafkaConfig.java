package com.ms.data.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

  @Value("${topic.name}")
  private String topicName;

  @Bean
  public NewTopic createTopic() {
    return new NewTopic(this.topicName, 1, (short) 1);
  }

}
