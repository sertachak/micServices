package com.sha.rabbitdemo.thread;

import java.util.concurrent.locks.ReentrantLock;

public class CounterLock {

    ReentrantLock lock = new ReentrantLock();

    private volatile int counter = 0;

    public void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public int getCounter() {
        try{
            lock.lock();
            return counter;
        } finally {
            lock.unlock();
        }
    }
}
