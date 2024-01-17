package com.sha.rabbitdemo.thread.signal;

public class SignalCarrier {

    //wait and notify is directly got from the Object class which is the absolute parent
    //of all classes in Java. So, any object can call wait and notify
    //wait and notify should be called from a synchronized block
    //wait releases the lock on the object and goes to waiting state
    //notify wakes up one of the waiting threads on the object

    public void doWait() throws InterruptedException {
        synchronized(this) {
            System.out.println(Thread.currentThread().getName() + ": Calling wait");
            this.wait();
            System.out.println(Thread.currentThread().getName() + ": Exited wait");
        }
    }

    public void doNotify() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ": Calling notify");
            this.notify();
            System.out.println(Thread.currentThread().getName() + ": Notify is called");
        }
    }
}
