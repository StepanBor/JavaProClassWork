package com.company;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Create new user (y/n)? ");
			System.out.println("type y - to create, or n - to login as existing user");
			String yN=scanner.nextLine();
			if(yN.equalsIgnoreCase("y")){
				System.out.println("Enter your login");
				String login = scanner.nextLine();
				System.out.println("Enter your password");
				String password=scanner.nextLine();
				User user=new User(login,password);
				int res = user.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occured: " + res);
					return;
				}
//				String postParam="login="+login+"&password"
////				URL url=new URL(Utils.getURL()+"/add");
////				HttpURLConnection conect= (HttpURLConnection) url.openConnection();
////
////				conect.setRequestMethod("POST");
////				conect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////				conect.setRequestProperty( "charset", "utf-8");
			}
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				Message m = new Message(login, text);
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
}
