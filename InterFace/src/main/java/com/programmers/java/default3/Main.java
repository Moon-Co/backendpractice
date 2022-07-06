package com.programmers.java.default3;

class Duck implements Swimmable, Walkable{

}
class Swan implements Flyable, Walkable,Swimmable{

}
public class Main {
    public static void main(String[] args) {
        new Duck().swim();
        new Duck().walk();
        new Swan().fly();
        Ability.sayHello();
    }
}
