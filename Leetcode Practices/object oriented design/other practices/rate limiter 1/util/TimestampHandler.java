package com.example.ratelimiter.util;

public class TimestampHolder {
    private long timestamp;

    public TimestampHolder() {
        this(System.currentTimeMillis());
    }

    public TimestampHolder(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
