package com.example.carcounter.model;

import java.time.LocalDateTime;

public class TrafficRecord implements Comparable<TrafficRecord> {
    private LocalDateTime startTime;
    private Integer carNum;

    public TrafficRecord(LocalDateTime startTime, Integer carNum) {
        this.startTime = startTime;
        this.carNum = carNum;
    }

    @Override
    public String toString() {
        return this.startTime.toString() + " " + this.carNum;
    }

    @Override
    public int compareTo(TrafficRecord otherRecord) {
        return otherRecord.getCarNum() != getCarNum() ?
                otherRecord.getCarNum() - getCarNum() : getStartTime().compareTo(otherRecord.getStartTime());
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public int getCarNum() {
        return this.carNum;
    }
}
