package com.sha.rabbitdemo.thread;

import java.util.concurrent.locks.ReentrantLock;

public class Calculation {

    ReentrantLock lock = new ReentrantLock(true);//if parameter is true, then it is a fair lock this also prevents starvation

    private long counter = 0;

    private void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    private void subtract() {
        lock.lock();
        try {
            counter--;
        } finally {
            lock.unlock();
        }
    }

    private long calculate(Calculate ...calculations) {
        try
        {
            lock.lock();
            for (Calculate calculation : calculations) {
                switch (calculation.getType()) {
                    case "ADD":
                        increment();
                        break;
                    case "SUBTRACT":
                        subtract();
                        break;
                }
            }
            return counter;
        } finally {
            lock.unlock();
        }
    }


    class Calculate {
        private final String type;

        public Calculate(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        System.out.println(calculation.calculate(calculation.new Calculate("ADD"), calculation.new Calculate("ADD"), calculation.new Calculate("SUBTRACT")));

        ReentrantLock lock = new ReentrantLock(true);

        Runnable runnable = () -> {
            lockTheLockAndSleep(lock, 1000);
        };

        Thread thread1 = new Thread(runnable, "thread1");
        Thread thread2 = new Thread(runnable, "thread2");
        Thread thread3 = new Thread(runnable, "thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void lockTheLockAndSleep(ReentrantLock lock, long sleepTime) {
        try {
            lock.lock();
            printThreadMessage("sleeping for " + sleepTime + "ms");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void printThreadMessage(String message) {
        System.out.println(Thread.currentThread().getName() + " " + message);
    }
}
