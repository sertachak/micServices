package com.sha.rabbitdemo.thread.signal;

public class SignalCarrier {

    //wait and notify is directly got from the Object class which is the absolute parent
    //of all classes in Java. So, any object can call wait and notify
    //wait and notify should be called from a synchronized block
    //wait releases the lock on the object and goes to waiting state
    //notify wakes up one of the waiting threads on the object

    private int threadsWaiting = 0;
    private int signals = 0;

    public void doWait() throws InterruptedException {
        synchronized(this) {
            if(signals >= 0) {
                System.out.println(Thread.currentThread().getName() + ": consumed signal at signal count: " + signals);
                signals--;
                return;
            }
            threadsWaiting++;
            System.out.println(Thread.currentThread().getName() + ": Calling wait");
            this.wait();
            threadsWaiting--;
            System.out.println(Thread.currentThread().getName() + ": Exited wait");
        }
    }

    public void doNotify() {
        synchronized (this) {
            if(threadsWaiting == 0) {
                signals++;
            }
            System.out.println(Thread.currentThread().getName() + ": Calling notify");
            this.notify();
            System.out.println(Thread.currentThread().getName() + ": Notify is called");
        }
    }
}
