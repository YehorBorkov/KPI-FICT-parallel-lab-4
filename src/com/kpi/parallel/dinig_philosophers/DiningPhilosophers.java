package com.kpi.parallel.dinig_philosophers;

import com.kpi.parallel.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiningPhilosophers {
    private static final int NO_OF_PHILOSOPHER = 5;
    static final Random NUMBERS = new Random(42);

    public static void main(String[] args) {
        List<Runnable> philosophers = new ArrayList<>();
        List<Fork>     forks        = new ArrayList<>();

        for (int i = 0; i < NO_OF_PHILOSOPHER; i++) {
            forks.add(new Fork("Fork " + (i + 1)));
        }
        for (int i = 0; i < NO_OF_PHILOSOPHER; i++) {
            philosophers.add(new Philosopher("Philosopher " + (i + 1), forks.get(i), forks.get((i + 1) % NO_OF_PHILOSOPHER), 1000));
        }

        Timer timer = new Timer(5000, philosophers);
        timer.start();
    }
}
