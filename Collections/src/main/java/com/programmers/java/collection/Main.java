package com.programmers.java.collection;

import java.util.*;
import java.util.function.Consumer;


public class Main {
    public static void main(String[] args) {
        //method chaining방식.
         new MyCollection<>(Arrays.asList("Acc","BB","Cb","Bc","E"))
                 .map(String::length)
                 .filter(i-> i%2==0)
                 .foreach(System.out::println);

//        c2.foreach(System.out::println);
        //System.out.println(s);

    }
}
