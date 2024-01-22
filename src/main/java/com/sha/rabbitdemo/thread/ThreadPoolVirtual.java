package com.sha.rabbitdemo.thread;

import java.util.concurrent.*;

public class ThreadPoolVirtual {

    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            Callable<String> callable = () -> "Thread name: " + Thread.currentThread().getName() + " Thread id: " + Thread.currentThread().threadId();

            final Future<String> submit = executorService.submit(callable);
            if (submit.isDone()) {
                try {
                    System.out.println(submit.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
