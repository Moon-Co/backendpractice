package com.programmers.java.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class Main3 {
    public static void main(String[] args) {
        //주사위를 100번 던져서 6이나올 확률을 구하시오.
//        Random r = new Random();
//        var count = Stream.generate(()->r.nextInt(6)+1)
//                .limit(100)
//                .filter(n->n==6)
//                .count();
//        System.out.println(count);
//        Random r = new Random();
//        int[] arr = Stream.generate(()->r.nextInt(10)+1)
//                .distinct() //중복제거
//                .limit(3)
//                .mapToInt(i->i)
//                .toArray();
//
//        System.out.println(Arrays.toString(arr));
        //0~200사이 값중에 랜덤값 5개 뽑아서 큰순서대로
        Random r = new Random();
        int[] arr = Stream.generate(()->r.nextInt(200))
                .limit(5)
                .sorted(Comparator.reverseOrder())
                .mapToInt(i->i)
                .toArray();
        System.out.println(Arrays.toString(arr));
    }
}
