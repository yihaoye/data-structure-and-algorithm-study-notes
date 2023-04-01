// 23th Mar 2023 interview

import java.util.*;

public class SlidingWindowRateLimiter {

    private static final int CUR_SEC_LIMIT = 3;
    private static final int TEN_SEC_LIMIT = 20;
    private static final int ONE_MIN_LIMIT = 60;

    private int[] curSecCnt;
    private Queue<Integer> tenSecCnt;
    private Queue<Integer> oneMinCnt;

    public SlidingWindowRateLimiter() {
        curSecCnt = new int[2];
        tenSecCnt = new LinkedList<>();
        oneMinCnt = new LinkedList<>();
    }

    /**
        @param requests: the given requests, assume already sorted by timestamp
     */
    public int process(int[] requests) {
        int res = 0;
        for (int request : requests) {
            updateCnts(request);
            if (!check()) res++;
            else append(request);
        }

        return res;
    }

    public void updateCnts(int request) {
        if (this.curSecCnt[0] != request) {
            this.curSecCnt[1] = 0;
        }

        while (!this.tenSecCnt.isEmpty() && request - this.tenSecCnt.peek() > 10) {
            this.tenSecCnt.poll();
        }

        while (!this.oneMinCnt.isEmpty() && request - this.oneMinCnt.peek() > 60) {
            this.oneMinCnt.poll();
        }
    }

    public boolean check() {
        return this.curSecCnt[1] < CUR_SEC_LIMIT && this.tenSecCnt.size() < TEN_SEC_LIMIT && this.oneMinCnt.size() < ONE_MIN_LIMIT;
    }

    public void append(int request) { // able to process the request
        this.curSecCnt[1]++;
        this.tenSecCnt.offer(request);
        this.oneMinCnt.offer(request);
    }
}
