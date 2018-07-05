package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetThread implements Runnable {
    private final Gson gson;
    private int n;
    private int privMessCount;
    private User currentUser;


    public GetThread(User currentUser) {
        gson = new GsonBuilder().create();
        this.currentUser = currentUser;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {

                URL url = new URL(Utils.getURL() + "/get?fromIndex=" + n + "&indexPrivate=" + privMessCount + "&fromUser=" + currentUser.hashCode());
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();

                try {
                    byte[] buf = requestBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);
//                    System.out.println(strBuf);
                    String[] messList = strBuf.split("kkk");
//                    System.out.println(messList[0]+messList[1]);
                    JsonMessages list = gson.fromJson(messList[0], JsonMessages.class);
                    if(messList[1]!=null){
//                        System.out.println(messList[1]+"aaaaa");
                    }
                    Message[] privateMessArr = gson.fromJson(messList[1], Message[].class);


                    if (list != null) {
                        for (Message m : list.getList()) {
                            System.out.println(m);
                            n++;
                        }
                    }
                    if (privateMessArr != null) {

                        for (Message m : privateMessArr) {
                            System.out.println(m);
                            privMessCount++;
                        }
                    }

                } finally {
                    is.close();
                }

                Thread.sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
