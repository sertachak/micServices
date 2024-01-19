package com.sha.rabbitdemo.thread.virtualThreads;

public class VirtualThreadUsage {

    public static void main(String[] args) {
        Thread vThread = Thread.ofVirtual().unstarted(createRunnable());
        vThread.start();
        try {
            vThread.join();
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }

    private static Runnable createRunnable() {
        return () -> {
            System.out.println("Runnable");
        };
    }
}
