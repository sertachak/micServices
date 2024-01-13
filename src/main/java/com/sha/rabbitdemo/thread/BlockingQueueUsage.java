package com.sha.rabbitdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import static java.lang.Thread.sleep;

public class BlockingQueueUsage {

    final static Logger log = LoggerFactory.getLogger(BlockingQueueUsage.class);


    public static void main(String[] args) {
        BlockingQueue<String> bQueue1 = new ArrayBlockingQueue<>(3);

        try(ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(2)){
            executorService.submit(QueueTaskProducer(bQueue1, log));
            executorService.submit(new Consumer(bQueue1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Runnable QueueTaskProducer(BlockingQueue bQueue1, Logger log) {
        return () -> {
            try {
                System.out.println("Producer started queue :" + bQueue1);
                int count = 0;
                while(count < 10){
                    bQueue1.put("Hello");
                    bQueue1.put("World");
                    bQueue1.put("Java");
                    System.out.println("Queue count: " + count);
                    count++;
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                log.error("Producer was interrupted");
            }
        };
    }

    private static class Consumer implements Runnable {
        BlockingQueue<String> bQueue1;
        Logger log = LoggerFactory.getLogger(Consumer.class);

        public Consumer(BlockingQueue<String> bQueue1) {
            this.bQueue1 = bQueue1;
        }

        @Override
        public void run() {
            System.out.println("Consumer started queue :" + bQueue1);
            try {
                while(true){
                    log.info("{}", bQueue1.take());
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
