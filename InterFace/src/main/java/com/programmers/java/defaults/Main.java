package com.programmers.java.defaults;

interface MyInterface{ //추상메소드로만 이루어진 클래스 : 인터페이스
    void method1(); //구현이 없어: 추상메소드
    default void sayHello(){
        System.out.println("Hello");
    }
}

public class Main implements MyInterface{
    public static void main(String[] args) {
        new Main().sayHello();
    }
    @Override
    public void sayHello(){
        System.out.println("Bye World");

    }

    @Override //추상메소드니까 반드시 오버라이드 해줘야함 여기는.
    public void method1() {
        throw new RuntimeException();
    }
}
