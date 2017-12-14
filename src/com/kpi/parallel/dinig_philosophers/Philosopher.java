package com.kpi.parallel.dinig_philosophers;

import static com.kpi.parallel.dinig_philosophers.DiningPhilosophers.NUMBERS;

public class Philosopher implements Runnable {
    private final String name;
    private final Fork leftFork;
    private final Fork rightFork;
    private final int actionTime;
    private int timesAte = 0;

    Philosopher(String name, Fork leftFork, Fork rightFork, int actionTime) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.actionTime = actionTime;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                think();
                leftFork.pickUp(this, "left");
                rightFork.pickUp(this, "right");
                eat();
                rightFork.putDown(this, "right");
                leftFork.putDown(this, "left");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name +  " was able to eat " + timesAte + " times");
    }

    private void think() throws InterruptedException {
        System.out.println(this + " is thinking");
        Thread.sleep(NUMBERS.nextInt(actionTime));
    }

    private void eat() throws InterruptedException {
        System.out.println(this + " is eating");
        timesAte++;
        Thread.sleep(NUMBERS.nextInt(actionTime));
    }

    @Override
    public String toString() {
        return name;
    }
}
