package com.sha.rabbitdemo.queue;

import java.util.PriorityQueue;
import java.util.Queue;

public class QueueUsage {


    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>(10, (o1, o2) -> {
            return o1.hashCode() - o2.hashCode();
        });

        queue.add(1);
        queue.add(10);
        queue.add(5);
        queue.add(3);

        queue.forEach(System.out::println);
    }
}
