package br.com.dbserver.votacao.messaging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig 
{
    @Value( "${app.kafka.topics.voto}" )
    private String votoTopic;

    @Bean
    public NewTopic votoTopic() 
    {
        return TopicBuilder.name( votoTopic )
                           .partitions( 1 )
                           .replicas( 1 )
                           .build();
    }
} 