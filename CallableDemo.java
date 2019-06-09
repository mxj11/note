package com.lxz.user.test.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("coming into call");
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }

    public static void main(String[] args) {
        try {
            FutureTask<Integer> futureTask = new FutureTask<>(new CallableDemo());
            new Thread(futureTask,"AA").start();
            Integer retResult = futureTask.get();
            System.out.println("***************main********************");
            int result = 1122;
//            Integer retResult = futureTask.get();
            System.out.println(retResult + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
