package com.sha.rabbitdemo.thread.signal;

public class ThreadSignalingExample {

    public static void main(String[] args) {
        SignalCarrier signalCarrier = new SignalCarrier();

        Thread waitingThread = new Thread ( () -> {
            try {
                signalCarrier.doWait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread notifyingThread = new Thread (signalCarrier::doNotify);

        waitingThread.start();
        notifyingThread.start();
    }

}
