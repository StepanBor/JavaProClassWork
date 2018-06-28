package com.gmail.stepan1983;

public class TestAnotation {

    @MyAno(a=1,b=3)
    public static int sum(int a, int b){
        return a+b;
    }
}
