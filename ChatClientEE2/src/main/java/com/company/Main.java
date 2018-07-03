package com.company;

import java.io.IOException;
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
