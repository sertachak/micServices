package com.sha.rabbitdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolMain {


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ThreadPoolMain.class);
        try(ClosableExecutorService executorService = new ClosableExecutorService(Executors.newFixedThreadPool(3))) {
            for(int i = 0; i< 10; i++) {
                int taskNumber = i;
                executorService.submit( () -> logger.info("FixedThreadPool" + " " + Thread.currentThread().getName() +  " " + "taskNo: "  + taskNumber));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    static class ClosableExecutorService implements AutoCloseable {

        private final ExecutorService executorService;

        public ClosableExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public void close() throws Exception {
            executorService.shutdown();
        }

        public void submit(Runnable runnable) {
            executorService.submit(runnable);
        }
    }
}
