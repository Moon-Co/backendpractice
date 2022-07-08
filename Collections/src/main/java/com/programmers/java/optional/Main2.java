package com.programmers.java.optional;

import com.programmers.java.collection.User;
import org.w3c.dom.ls.LSOutput;

import java.util.Optional;

public class Main2 {
    public static void main(String[] args) {
        User user;

        Optional<User> optionalUser = Optional.empty();
        optionalUser = Optional.of(new User(1,2));
        //User라는 데이터를 wrapping해서 운반. 값이 있을수도, 없을수도 있다.
        optionalUser.isEmpty();
        optionalUser.isPresent();
        optionalUser.ifPresent(User-> System.out.println(1));
        optionalUser.ifPresentOrElse(User-> System.out.println(1),
                ()-> System.out.println(2));
    }
}
