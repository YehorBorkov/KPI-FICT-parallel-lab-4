package com.kpi.parallel.sleeping_barber;

import com.kpi.parallel.RandomString;
import com.kpi.parallel.Timer;

import java.util.Arrays;
import java.util.List;

public class SleepingBarber {
    static final RandomString STRINGS = new RandomString(8, 42);

    public static void main(String[] args) {
        Shop shop = new Shop(8);
        Barber barber = new Barber("Barber", 800, shop);
        Street street = new Street(250, shop);

        List<Runnable> runnables = Arrays.asList(barber, street);

        Timer timer = new Timer(5000, runnables);
        timer.start();
    }
}
