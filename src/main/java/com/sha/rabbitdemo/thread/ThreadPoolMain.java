package com.sha.rabbitdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.io.Closeable;
import java.util.concurrent.*;

public class ThreadPoolMain {


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ThreadPoolMain.class);

        ExecutorService executorServiceManual = new ThreadPoolExecutor(3, 10, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(128));
        try(Closeable closeable = executorServiceManual::shutdown){
            final Future<?> submit = executorServiceManual.submit(() -> logger.info("ThreadPoolExecutor" + " " + Thread.currentThread().getName()));
            executorServiceManual.submit(() -> "This is the overloaded submit method which returns a Future object and accepts a Callable object");

            if(submit.isDone()) {
                logger.info("ThreadPoolExecutor" + " " + Thread.currentThread().getName() + " " + "is done");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }



        try(ClosableExecutorService executorService = new ClosableExecutorService(Executors.newFixedThreadPool(3,new CustomizableThreadFactory()))) {
            for(int i = 0; i< 10; i++) {
                int taskNumber = i;
                executorService.submit( () -> logger.info("FixedThreadPool" + " " + Thread.currentThread().getName() +  " " + "taskNo: "  + taskNumber));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private static Callable newCallable() {//which implements Callable interface is used in the submit method
        return new Callable() {//anonymous class
            @Override
            public Object call() throws Exception {
                return null;
            }
        };
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
