package com.gmail.stepan1983;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(JavaConfig.class);

//        MessageProvider provider=MessageFactory.getProvider();

        MessageRenderer renderer=MessageFactory.getRenderer();

        renderer.render();
    }

}
