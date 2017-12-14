package com.kpi.parallel.writers_readers;

import java.util.Objects;

import static com.kpi.parallel.writers_readers.WritersReaders.STRINGS;

public class Writer implements Runnable {
    private final String name;
    private final long waitingTime;
    private final long writingTime;
    private final Storage storage;
    private int wroteTotal;

    Writer(String name, long waitingTime, long writingTime, Storage storage) {
        this.name = name;
        this.waitingTime = waitingTime;
        this.writingTime = writingTime;
        this.storage = storage;
        this.wroteTotal = 0;
    }

    private void write() throws InterruptedException {
        synchronized (storage) {
            storage.writerIn(this);
            System.out.println(name + " enters storage");
            while (!storage.isWritable() || !storage.canWrite(this)) {
                storage.wait();
            }
            System.out.println(name + " starts writing");
            String newData = STRINGS.nextString();
            Thread.sleep((int)(writingTime * Math.random()));
            storage.putData(newData);
            System.out.println(name + " wrote " + newData + " and left");
            storage.writerOut();
        }
        wroteTotal++;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep((int)(waitingTime * Math.random()));
                write();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " wrote " + wroteTotal + " objects total");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return writingTime == writer.writingTime &&
                Objects.equals(name, writer.name);
    }

}