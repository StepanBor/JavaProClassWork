package com.gmail.stepan1983;

public class InternetMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Message from internet";
    }
}
