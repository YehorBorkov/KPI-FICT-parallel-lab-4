package com.kpi.parallel.sleeping_barber;

import java.util.LinkedList;
import java.util.Queue;

public class Shop {
    private final int capacity;
    private final Queue<Customer> customers;

    private boolean isBarberShaving;
    private Customer currentlyShaving;

    Shop(int capacity) {
        this.capacity = capacity;
        this.customers = new LinkedList<>();
        this.isBarberShaving = false;
        this.currentlyShaving = null;
    }

    public boolean isCustomer() {
        return !customers.isEmpty();
    }

    public boolean canEnter() {
        return customers.size() < capacity;
    }

    public boolean atBarberNow(Customer customer) {
        return isBarberShaving && customer.equals(currentlyShaving);
    }

    public Customer getCustomer() {
        isBarberShaving = true;
        currentlyShaving = customers.poll();
        return currentlyShaving;
    }

    public void freeBarber() {
        isBarberShaving = false;
        currentlyShaving = null;
    }

    public synchronized void customerIn(Customer c) {
        customers.offer(c);
        if (customers.size() == 1)
            this.notifyAll();
    }
}
