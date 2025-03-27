package br.com.dbserver.votacao.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true")
public class KafkaConfig {
    // Essa classe vazia é suficiente para evitar a inicialização do Kafka
    // quando a propriedade app.messaging.enabled está definida como false
} 