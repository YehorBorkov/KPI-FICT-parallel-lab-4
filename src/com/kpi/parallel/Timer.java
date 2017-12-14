package com.kpi.parallel;

import java.util.List;
import java.util.stream.Collectors;

public class Timer extends Thread {

    private long runTime;
    private List<Runnable> objects;

    public Timer(long runTime, List<Runnable> runnables) {
        this.runTime = runTime;
        objects = runnables;
    }

    @Override
    public void run() {
        List<Thread> threads = objects.stream().map(Thread::new).collect(Collectors.toList());
        threads.forEach(Thread::start);
        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
        System.out.println("Processes should stop now");
        threads.forEach(Thread::interrupt);
    }
}
