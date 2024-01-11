package com.sha.rabbitdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockByThreadPool {

    static Logger logger = LoggerFactory.getLogger(DeadlockByThreadPool.class);

    public static void main(String[] args) {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();


        try(ExecutorService executorService = Executors.newFixedThreadPool(2)){
            executorService.submit(newCallableLock1(lock1, lock2));
            executorService.submit(newCallableLock2(lock1, lock2));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    private static Callable<Boolean> newCallableLock1(Lock lock1, Lock lock2) {//which implements Callable interface is used in the submit method
        //anonymous class
        return () -> {
            logger.info("Thread {} started", Thread.currentThread().getName());
            lock1.lock();
            logger.info("Thread {} acquired lock1", Thread.currentThread().getName());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }

            logger.info("Thread {} waiting for lock2", Thread.currentThread().getName());
            lock2.lock();

            logger.info("Thread {} acquired lock2", Thread.currentThread().getName());

            lock1.unlock();
            logger.info("Thread {} released lock1", Thread.currentThread().getName());
            lock2.unlock();
            logger.info("Thread {} released lock2", Thread.currentThread().getName());

            return true;
        };
    }

    private static Callable<Boolean> newCallableLock2(Lock lock1, Lock lock2) {//which implements Callable interface is used in the submit method
        //anonymous class
        return () -> {
            logger.info("Thread {} started", Thread.currentThread().getName());
            lock2.lock();
            logger.info("Thread {} acquired lock2", Thread.currentThread().getName());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }

            logger.info("Thread {} waiting for lock1", Thread.currentThread().getName());
            lock1.lock();

            logger.info("Thread {} acquired lock1", Thread.currentThread().getName());

            lock2.unlock();
            logger.info("Thread {} released lock2", Thread.currentThread().getName());
            lock1.unlock();
            logger.info("Thread {} released lock1", Thread.currentThread().getName());

            return true;
        };
    }
}
