package com.sha.rabbitdemo.thread;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class CompareAndSwapExample {

    public static void main(String[] args) {

        CompareAndSwapLock lock = new CompareAndSwapExample().new CompareAndSwapLock();

        try {
            Thread t1 = new Thread(lockTest(lock));
            Thread t2 = new Thread(lockTest(lock));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    private static Runnable lockTest(CompareAndSwapLock lock) {
        return () -> {
            while (true) {
                while (!lock.lock()) {
                    System.out.println(MessageFormat.format("Waiting for lock thread: {0}", Thread.currentThread().getName()));
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println(MessageFormat.format("Lock acquired thread: {0}", Thread.currentThread().getName()));
                try {
                    lock.unlock();
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }


    private class CompareAndSwapLock {
        private AtomicBoolean isRunning = new AtomicBoolean(false);

        public boolean lock() {
            return isRunning.compareAndSet(false, true);
        }

        public boolean unlock() {
            return isRunning.compareAndSet(true, false);
        }
    }
}
