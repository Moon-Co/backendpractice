package com.programmers.java.optional;

import com.programmers.java.collection.User;

public class Main {
    public static void main(String[] args) {
        User emptyUser = User.EMPTY;
        User user = new User(22,"승식");
        //null이 절대 아니고, 무조건 무언가 있도록! ->초기값 있게 해주자.
        System.out.println(user);
    }
}
