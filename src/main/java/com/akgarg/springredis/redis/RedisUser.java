package com.akgarg.springredis.redis;

import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

public record RedisUser(String id, String name, String email, long createdAt) {

    static RedisUser random() {
        final Faker faker = new Faker();
        return new RedisUser(
                faker.random().hex(16).toLowerCase(),
                faker.name().fullName(),
                faker.internet().safeEmailAddress(),
                faker.date().past(24 * 365, TimeUnit.HOURS).getTime()
        );
    }

}
