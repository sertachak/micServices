package com.sha.rabbitdemo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolMain {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(int i = 0; i< 10; i++) {
            int taskNumber = i;
            executorService.submit( () -> System.out.println("FixedThreadPool" + " " + Thread.currentThread().getName() +  " " + "taskNo: "  + taskNumber));
        }

        executorService.shutdown();
    }
}
