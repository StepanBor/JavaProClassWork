package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.jws.soap.SOAPBinding;
import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        User user = velcomeMenu();
        try {
            Thread th = new Thread(new GetThread(user));
            th.setDaemon(true);
            th.start();
            while (true) {
                System.out.println("User list is:");
                System.out.println(getUserList());
                System.out.println();
                int swither1 = menu();
                switch (swither1) {
                    case 1:
                        System.out.println("Enter message");
                        String message = scanner.nextLine();
                        sendPublicMessage(message, user);
                        break;
                    case 2:
                        System.out.println("Enter message");
                        String message1 = scanner.nextLine();
                        int[] users = getIntFromString();
                        List<User> userList = getUserList();

                        List<User> sendTo = new ArrayList<>();
                        for (int j = 0; j < users.length; j++) {
                            sendTo.add(userList.get(users[j]));
                        }
                        sendPrivateMessage(message1, user, sendTo);
                        break;
                    case 4:
                        System.out.println(getUserList());
                        break;
                    case 5:
                        chatRoomMenu(user);
                }

                if (swither1 == 6) {
                    user.setOnline(Utils.getURL(), false);
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static List<User> getUserList() throws IOException {
        URL url = new URL(Utils.getURL() + "/addUser?getUserList=true");
        HttpURLConnection conect = (HttpURLConnection) url.openConnection();
        Gson gson = new GsonBuilder().create();
        int respCode = conect.getResponseCode();
        if (respCode != 200) {
            System.out.println("fail to get user list");
            return null;
        }
        InputStream is = conect.getInputStream();
        byte[] buf = GetThread.requestBodyToArray(is);
        String listJson = new String(buf, StandardCharsets.UTF_8);
        User[] userArr = gson.fromJson(listJson, User[].class);
        List<User> userList = new ArrayList<>(Arrays.asList(userArr));
        return userList;
    }

    public static void sendPrivateMessage(String message, User fromUser, List<User> toUsers) throws IOException {

        Message m = new Message(fromUser, toUsers, message);
        int res = m.send(Utils.getURL() + "/add");

        if (res != 200) { // 200 OK
            System.out.println("HTTP error occured: " + res);
        }
    }

    public static void sendPublicMessage(String message, User fromUser) throws IOException {
        Message m = new Message(fromUser, message);
        int res = m.send(Utils.getURL() + "/add");
        if (res != 200) { // 200 OK
            System.out.println("HTTP error occured: " + res);
        }
    }

    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1-Send public message");
        System.out.println("2-Send private message to users");
        System.out.println("3-Send mess to chat room");
        System.out.println("4-Get user list");
        System.out.println("5-Chatroom menu");
        System.out.println("6-Exit");
        System.out.println("Enter number of menu item");
        int i = scanner.nextInt();
        return i;
    }

    public static int[] getIntFromString() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user numbers from list bellow, use coma to separate numbers");
        String text = sc.nextLine();
        String[] textArr = text.split(",");
        int[] arr = new int[textArr.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(textArr[i]);
        }
        return arr;
    }

    public static User velcomeMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        User user;
        while (true) {
            System.out.println("Create new user (y/n)? ");
            System.out.println("type y - to create, or n - to login as existing user");
            String yN = scanner.nextLine();

            if (yN.equalsIgnoreCase("y")) {
                System.out.println("Enter your login");
                String login = scanner.nextLine();
                System.out.println("Enter your password");
                String password = scanner.nextLine();
                user = new User(login, password);
                int res = user.send(Utils.getURL(), "true");

                if (res != 200) { // 200 OK
                    System.out.println("Wrong user name: " + res);
                    continue;
                }
                System.out.println("You logged in ass-" + user.getLogin());
                user.setOnline(Utils.getURL(), true);
                break;

            } else {
                System.out.println("Enter your login");
                String login = scanner.nextLine();
                System.out.println("Enter your password");
                String password = scanner.nextLine();
                user = new User(login, password);
                int res = user.send(Utils.getURL(), "false");
                if (res != 200) { // 200 OK
                    System.out.println("Wrong user name or password: " + res);
                    continue;
                }
                System.out.println("You logged in ass-" + user.getLogin());
                user.setOnline(Utils.getURL(), true);
                break;
            }

        }
        return user;
    }

    public static void chatRoom (String roomName, List<User> users, int operationNum) throws IOException {
        Gson gson=new GsonBuilder().create();

        String jsonUsers=gson.toJson(users);
        String temp=roomName+"kkk"+jsonUsers+"kkk"+operationNum;

        URL url=new URL(Utils.getURL()+"/chatRoom");
        HttpURLConnection con= (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);


        try(OutputStream os=con.getOutputStream()){
            os.write(temp.getBytes(StandardCharsets.UTF_8));
            int i=con.getResponseCode();
        }

    }

    public static List<ChatRoom> getChatRoomList() throws IOException {
        List<ChatRoom> chatRoomList;
        URL url=new URL(Utils.getURL()+"/chatRoom");
        HttpURLConnection con= (HttpURLConnection) url.openConnection();

        Gson gson = new GsonBuilder().create();
        int respCode = con.getResponseCode();
        if (respCode != 200) {
            System.out.println("fail to get chatroom list");
            return null;
        }
        try(InputStream is = con.getInputStream()){
            byte[] buf = GetThread.requestBodyToArray(is);
            String listJson = new String(buf, StandardCharsets.UTF_8);
            ChatRoom[] chatRoomArr = gson.fromJson(listJson, ChatRoom[].class);
            chatRoomList = new ArrayList<>(Arrays.asList(chatRoomArr));
        }

        return chatRoomList;
    }

    public static void chatRoomMenu(User user) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1-Send chatroom message");
        System.out.println("2-Create chatroom");
        System.out.println("3-Quit from chatroom");
        System.out.println("4-Enter to chatroom");
        System.out.println("5-Get chatroom list");
        System.out.println("6-Exit");
        System.out.println("Enter number of menu item");
        int i = scanner.nextInt();

        switch (i){
            case 1:
                Scanner sc1=new Scanner(System.in);
                List<ChatRoom> chatroomList=getChatRoomList();
                System.out.println(getChatRoomList());
                System.out.println("Enter message");
                String message=sc1.nextLine();

                System.out.println("Choose chatroom number from list");
                int num=sc1.nextInt();
                List<User> userList=chatroomList.get(num).getUserList();
                sendPrivateMessage(message, user,userList);
                break;
            case 2:
                Scanner sc2=new Scanner(System.in);
                List<User> userList1=getUserList();
                System.out.println(userList1);
                System.out.println("Enter chatroom name");
                String chatRoomName=sc2.nextLine();

                System.out.println();
                int[] users = getIntFromString();

                List<User> sendTo = new ArrayList<>();
                for (int j = 0; j < users.length; j++) {
                    sendTo.add(userList1.get(users[j]));
                }

                chatRoom(chatRoomName,sendTo,1);
                System.out.println("chat room is:"+getChatRoomList());
                break;
            case 3:
                System.out.println("chat room list is:");
                System.out.println(getChatRoomList());
                Scanner sc3=new Scanner(System.in);
                System.out.println("Enter chatroom name");
                String chatRoomName1=sc3.nextLine();
                List<User> usersToQuit = new ArrayList<>();
                usersToQuit.add(user);
                chatRoom(chatRoomName1,usersToQuit,3);
                break;
            case 4:
                System.out.println("chat room list is:");
                System.out.println(getChatRoomList());
                Scanner sc4=new Scanner(System.in);
                System.out.println("Enter chatroom name");
                String chatRoomName2=sc4.nextLine();
                List<User> usersToEnter = new ArrayList<>();
                usersToEnter.add(user);
                chatRoom(chatRoomName2,usersToEnter,2);
                break;
            case 5:
                System.out.println(getChatRoomList());
                break;
        }

    }
}
