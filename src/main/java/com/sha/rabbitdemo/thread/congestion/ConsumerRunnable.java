package com.sha.rabbitdemo.thread.congestion;

import org.springframework.cglib.core.Block;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsumerRunnable implements Runnable{

    BlockingQueue<String> queue;

    public ConsumerRunnable(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    AtomicBoolean stopped = new AtomicBoolean(false);

    public void stop() {
        stopped.set(true);
    }


    @Override
    public void run() {
        System.out.println("Consumer started");

        int objectsConsumed = 0;
        while (!stopped.get()) {
                String task = getObjectFromQueue();
                if(task != null) {
                    objectsConsumed++;
                }
                System.out.println("Consumed: " + task);


        }

        while(!queue.isEmpty()) {
            String task = getObjectFromQueue();
            if(task != null) {
                objectsConsumed++;
            } else {
                break;
            }
        }
        System.out.println("Thread :" + Thread.currentThread().getName() + "  Objects consumed: " + objectsConsumed);
    }

    private String getObjectFromQueue() {
        try {
            return queue.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
