package com.programmers.java.lambda;

public class Main2 {
    public static void main(String[] args) {
        new Main2().loop(10, i -> System.out.println(i));
    }
    void loop(int n, MyConsumer<Integer> consumer){

        for(int i =0;i<n;i++){
            consumer.consume(i);
            //뭔가를 해야죠. i를 주고 뭔가 해라. -> 입력은 있고 출력은 따로 없어도 됨.
            //나는 해야될일만 하고, 구체적으로 어떤거하는가? 호출하는놈이 하셈.
        }
    }
}
