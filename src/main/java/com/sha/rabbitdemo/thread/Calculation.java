package com.sha.rabbitdemo.thread;

import java.util.concurrent.locks.ReentrantLock;

public class Calculation {

    ReentrantLock lock = new ReentrantLock();

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

    private long Calculate(Calculate ...calculations) {
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
}
