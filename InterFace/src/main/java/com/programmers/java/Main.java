package com.programmers.java;

interface MyRunnable{
    void myRun();
}
interface YourRunnable{
    void yourRun();
}
public class Main implements MyRunnable, YourRunnable{
    public static void main(String[] args) {
        Main m = new Main();
        m.myRun();
        m.yourRun();
    }
    //이미 있는 run()에서 추가해서 오버라이드 한거기때문에
    @Override
    public void myRun(){
        System.out.println("Hello MyRun");
    }
    public void yourRun(){
        System.out.println("Hello YourRun");
    }
}