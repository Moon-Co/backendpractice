package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class A{
    private final B b;

    A(B b) {
        this.b = b;
    }
}
class B{
    private final A a;
    B(A a) {
        this.a = a;
    }
}
class CircularConfig{
    //a가 b를 참조, b가 a를 참조
    @Bean
    public A a(B b){
        return new A(b);

    }
    @Bean
    public B b(A a){
        return new B(a);
    }

}
public class CircularDepTester {
    public static void main(String[] args) {
        var  AnnotationConfigApplicationContext=new AnnotationConfigApplicationContext(CircularConfig.class);
    }
}
