package com.kpi.parallel.producers_consumers;

import com.kpi.parallel.RandomString;
import com.kpi.parallel.Timer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProducersConsumers {
    static final RandomString STRINGS = new RandomString(15, 42);
    static final int STORAGE_SIZE = 15;

    public static void main(String[] args) {
        Queue<String> storage = new LinkedList<>();

        Semaphore access = new Semaphore(1);
        Semaphore full   = new Semaphore(STORAGE_SIZE);
        Semaphore empty  = new Semaphore(0);

        List<Runnable> runnables = Stream.of(
                new Producer("Producer 1", 200, storage, access, full, empty),
                new Consumer("Consumer 1", 100, storage, access, full, empty))
                .collect(Collectors.toList());

        Timer timer = new Timer(5000, runnables);
        timer.start();

    }
}
