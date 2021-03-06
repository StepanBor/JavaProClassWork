package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {
	
	private MessageList msgList = MessageList.getInstance();

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromIndexSting = req.getParameter("fromIndex");
		String fromIndexPrivate=req.getParameter("indexPrivate");
		String fromUser=req.getParameter("fromUser");
		int fromIndex = 0;
		int fromIndexPrivateInt=0;
		int fromUserInt=0;
		try {
			fromIndex = Integer.parseInt(fromIndexSting);
			fromIndexPrivateInt = Integer.parseInt(fromIndexPrivate);
			fromUserInt = Integer.parseInt(fromUser);
			if (fromIndex < 0) fromIndex = 0;
			if (fromIndexPrivateInt < 0) fromIndex = 0;
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
		}

		resp.setContentType("application/json");

//		System.out.println(msgList.userPrivateMessToJSON(fromUserInt,fromIndexPrivateInt));
		String json = msgList.toJSON(fromIndex)+"kkk"
				+msgList.userPrivateMessToJSON(fromUserInt,fromIndexPrivateInt);
//		String jsonPrivate=msgList.userPrivateMessToJSON(fromUserInt,fromIndexPrivateInt);
//		System.out.println(json+"WWWWWWWWWWWWWWWW");
		if (json != null) {
			OutputStream os = resp.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
			os.write(buf);

			//PrintWriter pw = resp.getWriter();
			//pw.print(json);
		}
	}
}
