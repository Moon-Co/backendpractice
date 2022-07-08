package com.programmers.java.stream;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main2 {
    public static void main(String[] args) {
//        Stream<Integer> s = Arrays.asList(1,2,3).stream();
//        Arrays.stream(new int[]{1,2,3}).boxed().toArray(Integer[]::new);

        //스트림 만들기 //generate==supplier
        Stream.generate(()-> 1)
                .limit(10)
                .forEach(System.out::println);

        //seed값부터 시작해서, 시드값이 두번째 인자로 들어간다.
        Stream.iterate(0,(i)->i+1);






        //boxed -> int ->Integer로 바꿔주는 함수.

    }
}
