package com.kpi.parallel.writers_readers;

public class Reader implements Runnable {
    private final String name;
    private final long readingTime;
    private final Storage storage;
    private int readTotal;

    Reader(String name, long readingTime, Storage storage) {
        this.name = name;
        this.readingTime = readingTime;
        this.storage = storage;
        this.readTotal = 0;
    }

    private void read() throws InterruptedException {
        String data;
        synchronized (storage) {
            while (!storage.isReadable()) {
                storage.wait();
            }
            data = storage.readerIn();
            System.out.println(name + " starts reading " + data);
        }
        Thread.sleep((int)(readingTime * Math.random()));
        synchronized (storage) {
            System.out.println(name + " stops reading");
            storage.readerOut();
        }
        readTotal++;
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
