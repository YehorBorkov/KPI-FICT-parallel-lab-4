package com.kpi.parallel.writers_readers;

import com.kpi.parallel.RandomString;
import com.kpi.parallel.Timer;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WritersReaders {
    static final Random NUMBERS = new Random(42);
    static final RandomString STRINGS = new RandomString(15, 42);

    public static void main(String[] args) {
        Storage dataStore = new Storage();

        List<Runnable> runnables = Stream.of(
                new Reader("Reader 1", 50, dataStore),
                new Reader("Reader 2", 60, dataStore),
                new Reader("Reader 3", 70, dataStore),
                new Reader("Reader 4", 80, dataStore),
                new Writer("Writer 1", 200, 50, dataStore),
                new Writer("Writer 2", 200, 35, dataStore))
                .collect(Collectors.toList());

        Timer timer = new Timer(5000, runnables);
        timer.start();
    }
}
