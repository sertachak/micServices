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


        //keep in the mind that the order of starting the threads is not guaranteed to be the order of execution
        //so, the notifying thread may start first and call notify before the waiting thread calls wait
        //in this case, the waiting thread will never wake up
        //this causes lost signal
        waitingThread.start();
        notifyingThread.start();
    }

}
