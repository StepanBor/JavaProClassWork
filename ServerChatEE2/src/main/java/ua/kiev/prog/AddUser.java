package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class AddUser extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getHeader("addUser").equals("true")) {
//            System.out.println(req.getHeader("addUser").equals("true"));
            byte[] buf = requestBodyToArray(req);
            String bufStr = new String(buf, StandardCharsets.UTF_8);
//            System.out.println(bufStr+"!!!!!!!!!!!!!!!!");
            User us = User.fromJSON(bufStr);
            System.out.println((us != null));

            System.out.println(us+"!!!!!!!!!!!!!!!!");
            System.out.println(msgList.getUserMap().isEmpty());

//            check and set status if not equals in user map


            if (us != null) {
                    System.out.println("HHHHHHHHHHHHHH");
                    msgList.addPrivateMess(us);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    return;

//                resp.setContentType("application/json");
//
//                Gson gson=new GsonBuilder().create();
//
//                String jsonUserList = gson.toJson(msgList.getUserList());
//
//                if (jsonUserList != null) {
//                    PrintWriter pw = resp.getWriter();
//                    pw.print(jsonUserList);
//                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        }else {
            byte[] buf = requestBodyToArray(req);
            String bufStr = new String(buf, StandardCharsets.UTF_8);
            User us = User.fromJSON(bufStr);

            if ((us != null & !msgList.getUserMap().isEmpty()) && msgList.getUserMap().containsKey(us)) {
                System.out.println("loginNNNNNNNNN");
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();

        resp.setContentType("application/json");

        String json = gson.toJson(msgList.getUserList());


        OutputStream os = resp.getOutputStream();
        byte[] buf = json.getBytes(StandardCharsets.UTF_8);
        os.write(buf);

        //PrintWriter pw = resp.getWriter();
        //pw.print(json);

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
