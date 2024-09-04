package com.kafka.producer;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.kafka.producer.enums.Color;
import com.kafka.producer.enums.DesignType;
import com.kafka.producer.enums.ProductType;
import com.kafka.producer.enums.UserId;
import com.kafka.producer.model.Event;
import com.kafka.producer.model.Product;
import com.kafka.producer.model.User;

@Component
public class EventGenerator {

    private final Faker faker = new Faker();
    
    public Event generateEvent() {
        return Event.builder()
                .user(generateRandomUser())
                .product(generateRandomProduct())
                .build();
    }

    private User generateRandomUser() {
        return User.builder()
                .userId(faker.options().option(UserId.class))
                .username(faker.name().lastName())
                .dateOfBirth(faker.date().birthday())
                .build();
    }

    private Product generateRandomProduct() {
        return Product.builder()
                .color(faker.options().option(Color.class))
                .type(faker.options().option(ProductType.class))
                .designType(faker.options().option(DesignType.class))
                .build();
    }
}
