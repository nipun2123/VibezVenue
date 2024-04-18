package com.VibezVenue.config;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.boostrap-servers}")
    private String boostrapServer;

    public Map<String, Object> producerConfig(){
        Map<String, Object> probs = new HashMap<>();
        probs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
        probs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        probs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return probs;
    }


    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}
