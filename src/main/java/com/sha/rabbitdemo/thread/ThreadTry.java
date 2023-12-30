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
              System.out.println("ThreadTry.runnable");
         };
       }
    }
}
