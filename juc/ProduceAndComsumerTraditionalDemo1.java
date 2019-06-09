package com.lxz.user.test.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProduceAndComsumerTraditionalDemo1 {
    static class ShareData {
        private Object lock;
        private int number = 0;

        ShareData(Object lock) {
            this.lock = lock;
        }

        public void increase() {
            synchronized (lock) {
                while (number != 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                number++;
                System.out.println(Thread.currentThread().getName() + "加1");
                lock.notifyAll();
            }
        }

        public void decrease() {
            synchronized (lock) {
                try {
                    while (number == 0) {
                        lock.wait();
                    }
                    number--;
                    System.out.println(Thread.currentThread().getName() + "减1");
                    lock.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static void main(String[] args) {
        ShareData data = new ShareData(new Object());
        for (int i = 0; i < 5; i++) {
            new Thread(data::increase).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(data::decrease).start();
        }
    }
}
