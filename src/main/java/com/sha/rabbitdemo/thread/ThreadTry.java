package com.sha.rabbitdemo.thread;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        InheritableThreadLocal<Integer> inheritableThreadLocal =  new InheritableThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return 1;
            }
        };
        Runnable runnableWithASharedValue = new Runnable() { // anonymous class

            private volatile int sharedThreadValue = 1;
            private volatile boolean sharedThreadValueChanged = false;
            Map<String, String> threadMap = new ConcurrentHashMap<>();
            @Override
            public void run() {
                System.out.println("ThreadTry.run"+ " " + Thread.currentThread().getName());

                while(!sharedThreadValueChanged) {
                    System.out.println("ThreadTry.run" + " " + Thread.currentThread().getName() + " "  + sharedThreadValue);
                    synchronized (this){ //keep in the mind that sync blocks do not guarantee fairness !!
                        System.out.println("ThreadTry.run" + " " + Thread.currentThread().getName() + " inside syncronized :  "  + sharedThreadValue);
                        sharedThreadValue++;

                        final Integer threadVal = threadLocal.get();
                        threadLocal.set(Math.min(threadVal == null ? sharedThreadValue : threadVal, sharedThreadValue));
                        inheritableThreadLocal.set(Math.min(inheritableThreadLocal.get(), sharedThreadValue));
                        if (sharedThreadValue == 5) {
                            sharedThreadValueChanged = true;
                        }

                        if(threadMap.containsKey("key")){
                            String val = threadMap.remove("key");
                            if(val == null){
                                System.out.println("Val null in the key");
                            }
                        } else {
                            threadMap.put("key", "val");
                        }
                    }
                }
                System.out.println("Min thread val: " + threadLocal.get());
                System.out.println("Min thread val inherited: " + inheritableThreadLocal.get());
            }
        };
        Thread thread2 = new Thread(runnableWithASharedValue);
        thread2.start();

        Thread thread3 = new Thread(runnableWithASharedValue);
        thread3.start();
    }
}

