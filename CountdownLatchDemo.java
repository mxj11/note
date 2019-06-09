package com.lxz.user.test.juc;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchDemo {
    private static CountDownLatch downLatch = new CountDownLatch(5);

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " doSomething");
                downLatch.countDown();
            }, "t" + i).start();

        }
        try {
            downLatch.await();
            System.out.println("main go on running");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
