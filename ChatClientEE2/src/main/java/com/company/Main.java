package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		User user;
		try {
			while (true){
				System.out.println("Create new user (y/n)? ");
				System.out.println("type y - to create, or n - to login as existing user");
				String yN=scanner.nextLine();
				if(yN.equalsIgnoreCase("y")){

					System.out.println("Enter your login");
					String login = scanner.nextLine();
					System.out.println("Enter your password");
					String password=scanner.nextLine();
					user=new User(login,password);
					user.setOnline(true);
					System.out.println(user);
					int res = user.send(Utils.getURL(),"true");

					if (res != 200) { // 200 OK
						System.out.println("Wrong user name: " + res);
						continue;
					}
					System.out.println("You logged in ass"+user.getLogin());
					break;
//				String postParam="login="+login+"&password"
////				URL url=new URL(Utils.getURL()+"/add");
////				HttpURLConnection conect= (HttpURLConnection) url.openConnection();
////
////				conect.setRequestMethod("POST");
////				conect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////				conect.setRequestProperty( "charset", "utf-8");
				} else {
					System.out.println("Enter your login");
					String login = scanner.nextLine();
					System.out.println("Enter your password");
					String password=scanner.nextLine();
					user=new User(login,password);
					user.setOnline(true);
					int res = user.send(Utils.getURL(),"false");

					if (res != 200) { // 200 OK
						System.out.println("Wrong user name or password: " + res);
						continue;
					}
					System.out.println("You logged in ass"+user.getLogin());
					break;

				}

			}

//			System.out.println("Enter your login: ");
//			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();


			while (true) {
				System.out.println("User list is:");
				System.out.println(getUserList());
				System.out.println("Enter your message: ");
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				System.out.println("Enter users names to whom you want send message");
				System.out.println("Separate user names with \",(coma)\" or press ENTER to send mess to all users");

				Message m = new Message(user, text);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occured: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	public static List<User> getUserList() throws IOException {
		URL url=new URL(Utils.getURL()+"/addUser?getUserList=true");
		HttpURLConnection conect= (HttpURLConnection) url.openConnection();
		Gson gson=new GsonBuilder().create();
		int respCode=conect.getResponseCode();
		if(respCode!=200){
			System.out.println("fail to get user list");
			return null;
		}
		InputStream is=conect.getInputStream();
		byte[] buf=GetThread.requestBodyToArray(is);
		String listJson=new String(buf, StandardCharsets.UTF_8);
		User[] userArr=gson.fromJson(listJson,User[].class);
		List<User> userList=new ArrayList<>(Arrays.asList(userArr));
		return userList;
	}

	public static void privateMessage(){

	}
}
