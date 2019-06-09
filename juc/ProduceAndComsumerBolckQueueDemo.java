package com.lxz.user.test.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProduceAndComsumerBolckQueueDemo {
    static class ShareRes {
        private BlockingQueue<String> blockingQueue = null;

        private volatile boolean flag = true;

        private AtomicInteger atomicInteger = new AtomicInteger();

        public ShareRes(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        public void produce() {
            String data = null;
            while (flag) {
                try {
                    data = atomicInteger.incrementAndGet() + "";
                    boolean result = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
                    if (result) {
                        System.out.println(Thread.currentThread().getName() + "生产产品成功:" + data);
                    } else {
                        System.out.println(Thread.currentThread().getName() + "生产产品失败:" + data);
                    }
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("停止生产");
        }

        public void comsumer() {
            while (flag) {
                try {
                    String result = blockingQueue.poll(2L,TimeUnit.SECONDS);
                    if (null == result || "".equals(result)) {
                        System.out.println("队列已经没有产品了，退出");
                        flag = false;
                        return;
                    }
                    System.out.println("消费成功" + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {
            this.flag = false;
        }
    }

    public static void main(String[] args) {
        ShareRes shareRes = new ShareRes(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            shareRes.produce();
        },"生产线程").start();

        new Thread(()->{
            shareRes.comsumer();
        },"消费线程").start();

        try {
            TimeUnit.SECONDS.sleep(7);
            System.out.println("main线程叫停生产");
            shareRes.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
