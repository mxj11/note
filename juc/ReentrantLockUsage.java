package com.lxz.user.test.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*

多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
AA打印5次，BB打印10次，CC打印15次，紧接着，AA打印5次，BB打印10次，CC打印15次，打印10轮。

 */
public class ReentrantLockUsage {
    static class ShareRes {
        private ReentrantLock lock = new ReentrantLock();
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();
        private int num = 1;

        public void print5(){
            lock.lock();
            try {
                while (num != 1) {
                    c1.await();
                }
                for (int i = 1; i <= 5; i++) {
                    System.out.println(i);
                }
                num = 2;
                c2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print10(){
            lock.lock();
            try {
                while (num != 2) {
                    c2.await();
                }
                for (int i = 1; i <= 10; i++) {
                    System.out.println(i);
                }
                num = 3;
                c3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print15(){
            lock.lock();
            try {
                while (num != 3) {
                    c3.await();
                }
                for (int i = 1; i <= 15; i++) {
                    System.out.println(i);
                }
                num = 1;
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ShareRes shareRes = new ShareRes();
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                shareRes.print5();
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                shareRes.print10();
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                shareRes.print15();
            }
        },"CC").start();
    }
}
