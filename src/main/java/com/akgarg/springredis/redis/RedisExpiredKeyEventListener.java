package com.akgarg.springredis.redis;

import jakarta.annotation.Nonnull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RedisExpiredKeyEventListener implements MessageListener {

    private static final Logger LOG = LogManager.getLogger(RedisExpiredKeyEventListener.class);

    @Override
    public void onMessage(@Nonnull final Message message, final byte[] pattern) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Key '{}' expired in redis", new String(message.getBody(), StandardCharsets.UTF_8));
        }
    }

}
