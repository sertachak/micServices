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

            int sharedThreadValue = 1;

            for (int i = 0; i < 10; i++) {
                System.out.println("ThreadTry.runnable" + threadName + " " + sharedThreadValue);
                sharedThreadValue++;
            }

        };

        Runnable runnableWithASharedValue = new Runnable() { // anonymous class

            private volatile int sharedThreadValue = 1;
            private volatile boolean sharedThreadValueChanged = false;
            @Override
            public void run() {
                System.out.println("ThreadTry.run"+ " " + Thread.currentThread().getName());

                while(!sharedThreadValueChanged) {
                    System.out.println("ThreadTry.run" + " " + Thread.currentThread().getName() + " "  + sharedThreadValue);
                    sharedThreadValue++;
                    if (sharedThreadValue == 5) {
                        sharedThreadValueChanged = true;
                    }
                }
            }
        };


        Thread thread2 = new Thread(runnableWithASharedValue);
        thread2.start();

        Thread thread3 = new Thread(runnableWithASharedValue);
        thread3.start();
    }
}

