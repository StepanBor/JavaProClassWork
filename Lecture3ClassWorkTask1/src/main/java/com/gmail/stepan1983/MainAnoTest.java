package com.gmail.stepan1983;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainAnoTest {

    public static void main(String[] args) {
//        Class<?> cls;


            try {
                Class<?> cls = Class.forName("com.gmail.stepan1983.TestAnotation");
                Method[] methods=cls.getMethods();
                MyAno myAno= (MyAno) cls.getAnnotation(MyAno.class);

                for (int i = 0; i <methods.length ; i++) {


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
