package com.kpi.parallel.sleeping_barber;

public class Barber implements Runnable {
    private final String name;
    private final int shavingTime;
    private int shavedCustomers;

    private final Shop shop;

    Barber(String name, int shavingTime, Shop shop) {
        this.name = name;
        this.shavingTime = shavingTime;
        this.shop = shop;
        this.shavedCustomers = 0;
    }

    private void work() throws InterruptedException{
        Customer customer;

        synchronized (shop) {
            while (!shop.isCustomer())
                shop.wait();
            customer = shop.getCustomer();
        }

        Thread.sleep((int)(shavingTime * Math.random()));
        System.out.println(name + " shaved " + customer);
        customer.goHome();
        shavedCustomers++;

        synchronized (shop) {
            shop.freeBarber();
            shop.notifyAll();
        }

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                work();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Barber " + name + " shaved " + shavedCustomers + " customers");
    }
}
