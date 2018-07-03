package ua.kiev.prog;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
	private Date date = new Date();
	private String from;
	private List<User> toUsers;
	private String text;

	public Message(String from, String text) {
		this.from = from;
		this.text = text;
	}

	public Message(String from, List<User> toUsers, String text) {
		this.from = from;
		this.toUsers = toUsers;
		this.text = text;
	}

	public String toJSON() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
	public static Message fromJSON(String s) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(s, Message.class);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date)
				.append(", From: ").append(from).append(", To: ").append(toUsers)
				.append("] ").append(text)
                .toString();
	}

	public int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)
				obj.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
	
		OutputStream os = conn.getOutputStream();
		try {
			String json = toJSON();
			os.write(json.getBytes(StandardCharsets.UTF_8));
			return conn.getResponseCode();
		} finally {
			os.close();
		}
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<User> getToUsers() {
		return toUsers;
	}

	public void setToUsers(List<User> toUsers) {
		this.toUsers = toUsers;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
