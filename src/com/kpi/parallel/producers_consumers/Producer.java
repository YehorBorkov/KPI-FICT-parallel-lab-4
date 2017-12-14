package com.kpi.parallel.producers_consumers;

import java.util.Queue;
import java.util.concurrent.Semaphore;

import static com.kpi.parallel.producers_consumers.ProducersConsumers.*;

public class Producer implements Runnable {
    private final String name;
    private final long productionTime;
    private final Queue<String> storage;
    private final Semaphore access, full, empty;
    private int producedTotal;

    Producer(String name,
                    long productionTime,
                    Queue<String> storage, Semaphore access, Semaphore full, Semaphore empty) {
        this.name = name;
        this.productionTime = productionTime;
        this.storage = storage;
        this.access = access;
        this.full = full;
        this.empty = empty;
        this.producedTotal = 0;
    }

    private void produce() throws InterruptedException {
        full.acquire();

        System.out.println(name + " started production");
        Thread.sleep((int)(productionTime * Math.random()));

        System.out.println(name + " is accessing storage");
        access.acquire();

        String result = STRINGS.nextString();
        storage.offer(result);
        empty.release();
        producedTotal++;
        System.out.println(name + " wrote " + result + " to storage and left");

        access.release();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                produce();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " wrote " + producedTotal + " objects total");
    }
}