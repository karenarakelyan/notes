package com.bestnotes.filegeneration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@EnableKafka
@Configuration
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerConfig {

  private final ConsumerFactory<String, String> consumerFactory;

  private final ObjectMapper objectMapper;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public KafkaConsumerConfig(
      final ConsumerFactory<String, String> consumerFactory,
      final ObjectMapper objectMapper,
      final KafkaTemplate<String, Object> kafkaTemplate) {
    this.consumerFactory = consumerFactory;
    this.objectMapper = objectMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    final ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
    factory.setReplyTemplate(kafkaTemplate);
    return factory;
  }

}
