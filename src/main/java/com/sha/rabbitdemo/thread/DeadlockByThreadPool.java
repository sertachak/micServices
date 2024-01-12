package com.sha.rabbitdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class DeadlockByThreadPool {

    static Logger logger = LoggerFactory.getLogger(DeadlockByThreadPool.class);

    /**
     * This is a deadlock example
     * One way to avoid deadlock is to lock the locks in the same order which is called lock reordering
     */
    public static void main(String[] args) {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();


        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
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
                sleep(1000);
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
                sleep(1000);
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

    private boolean lockBothLocks(Lock lock1, Lock lock2) {
        logger.info("Thread {} started", Thread.currentThread().getName());

        try {
            boolean lock1Acquired = lock1.tryLock(1000, TimeUnit.MILLISECONDS);

            if (!lock1Acquired) {
                logger.info("Thread {} could not acquire lock1", Thread.currentThread().getName());
                return false;
            }
        } catch (InterruptedException e) {
            logger.error("Thread {} could not acquire lock1 interrupted exception", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
            return false;
        }
        logger.info("Thread {} finished", Thread.currentThread().getName());
        try {
            boolean lock2Acquired = lock2.tryLock(1000, TimeUnit.MILLISECONDS);

            if (!lock2Acquired) {
                logger.info("Thread {} could not acquire lock1", Thread.currentThread().getName());
                lock1.unlock();
                return false;
            }
        } catch (InterruptedException e) {
            logger.error("Thread {} could not acquire lock1 interrupted exception", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }

    public Runnable runnableTimeout(Lock lock1, Lock lock2) {
        return () -> {
            logger.info("Thread {} started", Thread.currentThread().getName());
            boolean notFinalized = true;

           while(notFinalized){
               int counter = 0;

               while(!lockBothLocks(lock1, lock2)){
                   counter++;
                   logger.info("Thread {} could not acquire both locks", Thread.currentThread().getName());
                   try {
                       sleep(1000);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
               if(counter > 0){
                    logger.info("Thread {} acquired both locks after {} failures", Thread.currentThread().getName(), counter);
               }

               //
               notFinalized = false;
           }

            lock1.unlock();
            lock2.unlock();
        };
    }
}
