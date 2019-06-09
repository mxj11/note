package com.lxz.user.test.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProduceAndComsumerTraditionalDemo {
    static class ShareData {
        private int number = 0;
        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void increase() {
            try {
                lock.lock();
                while (number != 0) {
                    condition.await();
                }
                number++;
                System.out.println(Thread.currentThread().getName() + "加1");
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void decrease() {
            try {
                lock.lock();
                while (number == 0) {
                    condition.await();
                }
                number--;
                System.out.println(Thread.currentThread().getName() + "减1");
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ShareData data = new ShareData();
        for (int i = 0; i < 5; i++) {
            new Thread(data::increase).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(data::decrease).start();
        }
    }
}
