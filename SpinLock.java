package com.lxz.user.test.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();
    private void lock() {
        Thread thread = Thread.currentThread();
        System.out.println("get lock");
        while (!atomicReference.compareAndSet(null,thread)) {

        }
    }

    private void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println("unlock");
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        new Thread(()->{
            spinLock.lock();
            try {
                System.out.println("threadA do something");
                TimeUnit.SECONDS.sleep(4);
                spinLock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            spinLock.lock();
            try {
                System.out.println("threadB do something");
                TimeUnit.SECONDS.sleep(2);
                spinLock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
