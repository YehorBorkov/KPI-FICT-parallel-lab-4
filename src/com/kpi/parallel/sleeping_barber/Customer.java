package com.kpi.parallel.sleeping_barber;

import static com.kpi.parallel.sleeping_barber.SleepingBarber.STRINGS;

public class Customer implements Runnable {
    private final String name;
    private final Shop shop;

    Customer(Shop shop) {
        this.name = "Customer " + STRINGS.nextString();
        this.shop = shop;
    }

    private void goShaving() throws InterruptedException {
        synchronized (shop) {
            while (!shop.atBarberNow(this))
                shop.wait();
            System.out.println(name + " went shaving");
            Thread.currentThread().interrupt();
        }
    }

    public void goHome() {
        System.out.println(name + " has left the barber");
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                goShaving();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
