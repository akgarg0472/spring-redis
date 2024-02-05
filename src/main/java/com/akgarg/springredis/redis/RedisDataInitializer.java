package com.akgarg.springredis.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
public class RedisDataInitializer implements CommandLineRunner {

    private static final Logger LOG = LogManager.getLogger(RedisDataInitializer.class);

    private final RedisService redisService;

    public RedisDataInitializer(final RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void run(final String... args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            final RedisUser user = RedisUser.random();
            redisService.insertKeyValueWithoutExpiration("user:%s".formatted(user.id()), user);
        }

        for (int i = 1; i <= 5; i++) {
            final RedisUser user = RedisUser.random();
            redisService.insertKeyValueWithExpiration("user:%s".formatted(user.id()), user, Duration.ofMillis(10 * 1000L));
        }

        final Set<String> users = redisService.getAllKeyValues("user:*");
        LOG.info("Found {} users: {}", users.size(), users);
    }

}
