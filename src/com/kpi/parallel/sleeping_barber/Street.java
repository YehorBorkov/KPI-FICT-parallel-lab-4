package com.kpi.parallel.sleeping_barber;

import java.util.ArrayList;
import java.util.List;

public class Street implements Runnable {
    private final long timeToNewCustomer;
    private int customersSent, customersSkipped;

    private final List<Thread> runningCustomers;
    private final Shop shop;

    Street(long timeToNewCustomer, Shop shop) {
        this.timeToNewCustomer = timeToNewCustomer;
        this.runningCustomers = new ArrayList<>();
        this.shop = shop;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep((int)(timeToNewCustomer * Math.random()));
                customersSent++;
                if (shop.canEnter()) {
                    Customer customer = new Customer(shop);

                    Thread customerThread = new Thread(customer);
                    customerThread.start();
                    runningCustomers.add(customerThread);

                    shop.customerIn(customer);
                    System.out.println(customer + " has entered the shop");
                }
                else
                    customersSkipped++;
            }
        } catch (InterruptedException e) {
            runningCustomers.stream().filter(x -> !x.isInterrupted()).forEach(Thread::interrupt);
            Thread.currentThread().interrupt();
        }
        System.out.println(customersSent + " customers came from the street, " + customersSkipped + " of them were skipped");
    }
}
