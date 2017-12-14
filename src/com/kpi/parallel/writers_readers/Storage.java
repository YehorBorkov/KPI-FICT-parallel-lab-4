package com.kpi.parallel.writers_readers;

import java.util.*;

import static com.kpi.parallel.writers_readers.WritersReaders.NUMBERS;
import static com.kpi.parallel.writers_readers.WritersReaders.STRINGS;

public class Storage {
    private Queue<Writer> writerQueue;

    private int readersCount;
    private List<String> data;

    Storage() {
        this.writerQueue   = new LinkedList<>();
        this.readersCount  = 0;
        this.data          = new ArrayList<>(Arrays.asList(STRINGS.nextString(), STRINGS.nextString(), STRINGS.nextString()));
    }

    public boolean isReadable() {
        return writerQueue.isEmpty();
    }

    public boolean isWritable() {
        return this.readersCount == 0;
    }

    public String readerIn() {
        this.readersCount++;
        return data.get(NUMBERS.nextInt(data.size()));
    }

    public void readerOut() {
        this.readersCount--;
        if (this.readersCount == 0) {
            System.out.println("All readers left");
            this.notifyAll();
        }
    }

    public void writerIn(Writer writer) {
        writerQueue.offer(writer);
    }

    public boolean canWrite(Writer writer) {
        return writerQueue.peek().equals(writer);
    }

    public void putData(String newData) {
        data.add(newData);
    }

    public void writerOut() {
        writerQueue.poll();
        this.notifyAll();
        if (writerQueue.isEmpty())
            System.out.println("All writers left");
    }

}
