package ua.kiev.prog;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.net.*;

public class TestMain {

    public static void main(String[] args) throws IOException {
        OkHttpClient client=new OkHttpClient();
        HttpUrl url=new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8080).build();

        System.out.println(url);

        URL url2=new URL(url.toString());

        HttpURLConnection conect= (HttpURLConnection) url2.openConnection();
        conect.setRequestMethod("POST");
        conect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conect.setRequestProperty( "charset", "utf-8");
    }




}
