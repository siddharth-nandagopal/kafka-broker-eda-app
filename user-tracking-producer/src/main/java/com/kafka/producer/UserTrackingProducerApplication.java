package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.kafka.producer.model.Event;
import com.kafka.producer.model.User;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@Slf4j
public class UserTrackingProducerApplication {

	

	private static final String TOPIC = "user-tracking";

	private static final String KAFKA_SERVER = "10.244.2.2:9092,10.244.3.2:9092,10.244.1.3:9092,10.244.2.2:29093,10.244.3.2:29093,10.244.1.3:29093";

	// @Autowired
    // private KafkaTemplate<String, User> kafkaTemplate;

    // kafkaTemplate.send(TOPIC, new User(name, "Technology", 12000L));


	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(UserTrackingProducerApplication.class, args);

		EventGenerator eventGenerator = new EventGenerator();

        Properties props = new Properties();

        // Because now we make use of the broker deployed on a docker container
        // taken from the docker-compose.yml of
        // https://github.com/confluentinc/cp-all-in-one/tree/7.2.1-post/cp-all-in-one-community
        // props.put("bootstrap.servers", "localhost:9093,localhost:9094");
        props.put("bootstrap.servers", KAFKA_SERVER);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for(int i = 1; i <= 10; i++) {
            log.info("Generating event #" + i);

            Event event = eventGenerator.generateEvent();

            String key = extractKey(event);
            String value = extractValue(event);

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, key, value);

            log.info("Producing to Kafka the record: " + key + ":" + value);
            producer.send(producerRecord);

            sleep(1000);
        }

        producer.close();
	}

	private static String extractKey(Event event) {
		return event.getUser().getUserId().toString();
	}

	private static String extractValue(Event event) {
		return String.format("%s,%s,%s", event.getProduct().getType(), event.getProduct().getColor(),
				event.getProduct().getDesignType());
	}

}
