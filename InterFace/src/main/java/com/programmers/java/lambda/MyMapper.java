package com.programmers.java.lambda;

public interface MyMapper<IN, OUT>{
    OUT map(IN s);
}
