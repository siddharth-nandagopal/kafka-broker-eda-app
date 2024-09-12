package com.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.util.Arrays.asList;

@SpringBootApplication
@Slf4j
public class UserTrackingConsumerApplication {

	private static final String TOPIC = "user-tracking";
	private static final String KAFKA_SERVER = "kafka01:29192,kafka02:29292,kafka03:29392";
    public static void main(String[] args) {

		SpringApplication.run(UserTrackingConsumerApplication.class, args);

        SuggestionEngine suggestionEngine = new SuggestionEngine();

        Properties props = new Properties();

        // props.put("bootstrap.servers", "localhost:9093,localhost:9094");
        props.put("bootstrap.servers", KAFKA_SERVER);
        props.put("group.id", TOPIC + "-consumer"); // helps to organize/group multiple consumers
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {

            consumer.subscribe(asList(TOPIC));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    suggestionEngine.processSuggestions(record.key(), record.value());
                }
            }
        } catch (Exception e) {
            log.error(String.format("An exception was raised whilst trying to consume from %s", TOPIC), e);
            throw new RuntimeException(e);
        }
    }

}
