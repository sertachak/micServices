package com.sha.rabbitdemo.thread;

public class FalseSharing {

    public static void main(String[] args) {

        //Counter counter = new FalseSharing().new Counter();
        //Counter counter2 = counter; // creates false sharing. The variables should we volatile in order to write them to main memory and not cache
        //Counter counter2 = new FalseSharing().new Counter();
        Counter2 counter = new FalseSharing().new Counter2();
        Counter2 counter2 = counter;

        final long c = 1_000_000_000;

        Thread t1 = new Thread(() -> {
            long start = System.currentTimeMillis();
            for (int i = 0 ; i < c ; i++) {
                counter.increment();
            }
            long end = System.currentTimeMillis();
            System.out.println("Time taken: " + (end - start));
        });
        Thread t2 = new Thread(() -> {
            long start = System.currentTimeMillis();
            for (int i = 0 ; i < c ; i++) {
                counter2.increment2();
            }
            long end = System.currentTimeMillis();
            System.out.println("Time taken: " + (end - start));
        });
        t1.start();
        t2.start();

    }

    private class Counter {
        private volatile long counter = 0;
        private volatile long counter2 = 0;

        public void increment() {
            counter++;
        }

        public void increment2() {
            counter2++;
        }
    }

    private class Counter2 {

        @jdk.internal.vm.annotation.Contended//this field is annotated to state that it should not stored in the same cache line as other fields
        private volatile long counter = 0;
        private volatile long counter2 = 0;

        public void increment() {
            counter++;
        }

        public void increment2() {
            counter2++;
        }
    }
}
