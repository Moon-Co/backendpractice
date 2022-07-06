package com.programmers.java.poly;

public class UserService implements Login{
    private Login login; //서비스입장에서는 내부의 정보이기 때문에
    //로그인에 의존한다.
    //의존성을 외부에 맡긴다면 여러가지 기능을 수행가능.
    //추상체와 결합을 하게되면. 결합도가 낮아진다. (추상체랑 결합하는게 다양한거 사용가능)
    //의존성을 외부로부터 전달받았다. =>의존성을 주입당했다.
    //의존성 주입. Dependency Injection (DI)
    //Dependency Inversion 되었다. 추상체를 중간에 둬서 추상체를 통해 의존해라!
    public UserService(Login login){
        this.login = login;
        //내부의 정보를 드러나지 않도록 하려면 private으로 변수, public객체로 접근.
        //login할 수 있는 구현체가 들어오면, 그걸 login
    }
    @Override
    public void login() {
        login.login();
    }
}
