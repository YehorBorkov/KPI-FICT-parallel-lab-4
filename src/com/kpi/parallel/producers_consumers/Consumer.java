package com.kpi.parallel.producers_consumers;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    private final String name;
    private final long readingTime;
    private final Queue<String> storage;
    private final Semaphore access, full, empty;
    private int readTotal;

    Consumer(String name,
                    long readingTime,
                    Queue<String> storage,
                    Semaphore access, Semaphore full, Semaphore empty) {
        this.name = name;
        this.readingTime = readingTime;
        this.storage = storage;
        this.access = access;
        this.full = full;
        this.empty = empty;
        this.readTotal = 0;
    }

    private void read() throws InterruptedException {
        empty.acquire();

        System.out.println(name + " is accessing storage");
        access.acquire();

        String result = storage.poll();
        full.release();
        readTotal++;
        System.out.println(name + " took " + result + " from storage and left");
        access.release();

        Thread.sleep((int)(readingTime * Math.random()));
        System.out.println(name + " successfully read " + result);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                read();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " read " + readTotal + " objects total");
    }
}
