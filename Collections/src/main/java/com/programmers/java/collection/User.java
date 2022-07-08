package com.programmers.java.collection;

public class User {
    public static User EMPTY = new User(0,"");
    private int age;
    private String name;

    public User(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public boolean isOver18(){
        return age>=18;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
