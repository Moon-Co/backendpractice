package com.programmers.java.func;

public class Main2 {
    public static void main(String[] args) {
        new MyRunnable(){
            @Override
            public void run() {
                System.out.println("Hello");
            }
        }.run();

        MyRunnable r = () -> System.out.println("Hello");
        MySupply s1 = () ->"Hello World";

        MyRunnable r3 = () ->{
            MySupply s2 = () -> "Hello Hello Hello";
            System.out.println(s2.supply());
        };
        r3.run();

    }
}
