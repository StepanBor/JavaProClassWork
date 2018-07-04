package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class Message {
	private Date date = new Date();
	private User fromUser;
	private List<User> toUsers;
	private String text;

	public Message(User fromUser, String text) {
		this.fromUser = fromUser;
		this.text = text;
	}

	public Message(User fromUser, List<User> toUsers, String text) {
		this.fromUser = fromUser;
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
	


	public int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
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

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
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

	@Override
	public String toString() {
		return "Message{" +
				"date=" + date +
				", fromUser='" + fromUser + '\'' +
				", toUsers=" + toUsers +
				", text='" + text + '\'' +
				'}';
	}
}
