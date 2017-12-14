package com.kpi.parallel.dinig_philosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final Lock up = new ReentrantLock();
    private final String name;

    Fork(String name) {
        this.name = name;
    }

    public void pickUp(Philosopher philosopher, String which) throws InterruptedException {
        up.lockInterruptibly();
        System.out.println(philosopher + " picked up " + which + " " + this);
    }

    public void putDown(Philosopher philosopher, String which) {
        up.unlock();
        System.out.println(philosopher + " put down " + which + " " + this);
    }

    @Override
    public String toString() {
        return name;
    }
}
