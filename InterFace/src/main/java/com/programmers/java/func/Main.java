package com.programmers.java.func;

public class Main {
    public static void main(String[] args) {
        MyRunnable r = new MyRunnable(){
            @Override
            public void run() {
                MySupply s = new MySupply() {
                    @Override
                    public String supply() {
                        return "Hello Hello";
                    }
                };
                System.out.println(s.supply());
            }
        };
        r.run();

    }
}
