package com.lxz.user.test.juc;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println("放值:1");
                synchronousQueue.put(1);
                System.out.println("放值:2");
                synchronousQueue.put(2);
                System.out.println("放值:3");
                synchronousQueue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("取值：" + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(4);
                System.out.println("取值：" + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(4);
                System.out.println("取值：" + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}
