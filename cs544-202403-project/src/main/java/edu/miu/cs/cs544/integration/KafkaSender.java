package edu.miu.cs.cs544.integration;

import edu.miu.cs.cs544.service.contract.EmailPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
    @Autowired
    KafkaTemplate<String, EmailPayload> kafkaTemplate;
    public void send(String topic, EmailPayload emailPayload){
        kafkaTemplate.send(topic, emailPayload);
    }
}
