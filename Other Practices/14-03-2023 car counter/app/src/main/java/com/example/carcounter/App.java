package com.example.carcounter;

import java.io.*;
import java.math.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import com.example.carcounter.exception.*;
import com.example.carcounter.model.*;

public class App {
    private final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601);
    private List<TrafficRecord> originRecords;
    private List<TrafficRecord> sortedRecords;
    private Map<String, Integer> dateCount; // According to real world, Integer should be enough for counting car traffic per day, apply Long or BigInteger if needed
    private BigInteger totalCount;

    public static void main(String[] args) {
        // ...
    }

    public void processFile(String filePath) {
        this.originRecords = new ArrayList<>();
        this.sortedRecords = new ArrayList<>();
        this.dateCount = new LinkedHashMap<>();
        this.totalCount = BigInteger.ZERO;

        if (filePath == null || filePath.length() == 0) {
            System.out.println("file path is empty.");
            return;
        }

        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String record = reader.nextLine();
                String[] recordDetails = record.split(" ");

                LocalDateTime startTime = LocalDateTime.parse(recordDetails[0], formatter);
                Integer carNum = Integer.valueOf(recordDetails[1]);
                if (carNum < 0) {
                    throw new DataException("car number should not be negative.");
                }
                TrafficRecord trafficRecord = new TrafficRecord(startTime, carNum);
                this.originRecords.add(trafficRecord);
                this.sortedRecords.add(trafficRecord);

                String date = startTime.toLocalDate().toString();
                this.dateCount.put(date, this.dateCount.getOrDefault(date, 0) + carNum);

                this.totalCount = this.totalCount.add(BigInteger.valueOf(carNum));
            }
            Collections.sort(this.sortedRecords);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List<TrafficRecord> mostCountRecords(int topK) {
        if (this.sortedRecords == null || this.sortedRecords.size() < topK) return new ArrayList<>();
        return this.sortedRecords.subList(0, topK);
    }

    public Map<String, Integer> getDateCount() {
        if (this.dateCount == null) return new LinkedHashMap<>();
        return this.dateCount;
    }

    public BigInteger getTotalCount() {
        if (this.totalCount == null) return BigInteger.ZERO;
        return this.totalCount;
    }

    public int getLeastCount(long secondsWindow) {
        int cur = 0, min = Integer.MAX_VALUE;
        Queue<TrafficRecord> queue = new LinkedList<>();
        for (TrafficRecord record : originRecords) {
            long curTimestamp = record.getStartTime().toEpochSecond(ZoneOffset.UTC);
            while (!queue.isEmpty()) {
                long preTimestamp = queue.peek().getStartTime().toEpochSecond(ZoneOffset.UTC);
                if (curTimestamp - preTimestamp < secondsWindow) {
                    break;
                }
                min = Math.min(min, cur);
                cur = cur - queue.peek().getCarNum();
                queue.poll();
            }
            queue.add(record);
            cur = cur + record.getCarNum();
        }
        min = Math.min(min, cur);

        return min;
    }
}