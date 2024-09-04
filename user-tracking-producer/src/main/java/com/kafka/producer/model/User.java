package com.kafka.producer.model;

import java.util.Date;

import com.kafka.producer.enums.UserId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private UserId userId;

    private String username;

    private Date dateOfBirth;
}
