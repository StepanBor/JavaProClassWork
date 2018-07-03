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
		try {
			fromIndex = Integer.parseInt(fromIndexSting);
			if (fromIndex < 0) fromIndex = 0;
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
		}

		resp.setContentType("application/json");
		
		String json = msgList.toJSON(fromIndex);

		if (json != null) {
			OutputStream os = resp.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
			os.write(buf);

			//PrintWriter pw = resp.getWriter();
			//pw.print(json);
		}
	}
}
