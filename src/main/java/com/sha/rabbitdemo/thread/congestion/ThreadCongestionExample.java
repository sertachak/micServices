package com.sha.rabbitdemo.thread.congestion;

import java.sql.SQLOutput;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCongestionExample {

    public static void main(String[] args) {
        final int TASKS = 1_000;

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(TASKS);

        ConsumerRunnable[] consumers = new ConsumerRunnable[3];

        synchronized (ThreadCongestionExample.class) {
            for (int i = 0 ; i < consumers.length ; i++) {
                consumers[i] = new  ConsumerRunnable(queue);
                Thread t = new Thread(consumers[i]);
                t.start();
            }
        }

        Thread producingThread = new Thread(() -> {
            for (int i = 0 ; i < TASKS ; i++) {
                try {
                    queue.put("Task " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("All items produced");
            System.out.println("Objects produced: " + TASKS);

            synchronized (ThreadCongestionExample.class) {
                for (ConsumerRunnable consumer : consumers) {
                    consumer.stop();
                }
            }
        });

        producingThread.start();
    }

}
