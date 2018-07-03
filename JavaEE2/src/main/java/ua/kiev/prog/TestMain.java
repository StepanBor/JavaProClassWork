package ua.kiev.prog;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class TestMain {

    public static void main(String[] args) {
        OkHttpClient client=new OkHttpClient();
        HttpUrl url=new HttpUrl.Builder()
                .scheme("http")
                .host("192.168.1.83")
                .port(8080).build();

        System.out.println(url);
    }




}
