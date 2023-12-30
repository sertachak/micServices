package com.sha.rabbitdemo.thread;

public class ThreadTry {

    public static void main(String[] args) {
        System.out.println("ThreadTry.main");
        Thread thread = new Thread(new Runnable() { // anonymous class
            @Override
            public void run() {
                System.out.println("ThreadTry.run");
            }
        });
        thread.start();

        Runnable runnable = () -> { // lambda expression
            String threadName = Thread.currentThread().getName();
            System.out.println("ThreadTry.runnable" + threadName);

            for (int i = 0; i < 10; i++) {
                System.out.println("ThreadTry.runnable" + threadName + " " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        Thread thread2 = new Thread(runnable);
        thread2.setDaemon(true);
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

