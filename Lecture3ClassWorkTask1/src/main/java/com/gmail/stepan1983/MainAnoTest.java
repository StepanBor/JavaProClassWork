package com.gmail.stepan1983;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainAnoTest {

    public static void main(String[] args) {
//        Class<?> cls;


            try {
                Class<?> cls = Class.forName("com.gmail.stepan1983.TestAnotation");
                Method[] methods=cls.getMethods();


                for (int i = 0; i <methods.length ; i++) {

                    MyAno myAno= (MyAno) methods[i].getAnnotation(MyAno.class);

                    if(methods[i].isAnnotationPresent(MyAno.class)){
                        System.out.println(methods[i].invoke(null,myAno.a(),myAno.b()));
                    }

                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

    }

}
