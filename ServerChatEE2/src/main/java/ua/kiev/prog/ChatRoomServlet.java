package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class ChatRoomServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();
    private Gson gson=new GsonBuilder().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        byte[] buf = requestBodyToArray(request);
        String bufStr = new String(buf, StandardCharsets.UTF_8);
        String[] stringArr=bufStr.split("kkk");
        User[] users=gson.fromJson(stringArr[1],User[].class);
        int i=Integer.parseInt(stringArr[2]);

        switch (i){
            case 1:
                msgList.addChatRoom(stringArr[0],Arrays.asList(users));
                break;
            case 2:
                msgList.addUserToChatRoom(stringArr[0],Arrays.asList(users));
                break;
            case 3:
                msgList.removeFromChatRoom(stringArr[0],Arrays.asList(users));
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();

        response.setContentType("application/json");

        String json = gson.toJson(msgList.getUserList());


        try(OutputStream os = response.getOutputStream()) {
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
            os.write(buf);
        }
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
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
