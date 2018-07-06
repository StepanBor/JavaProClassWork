package com.company;

import java.util.List;

public class ChatRoom {

    private String roomName;
    private List<User> userList;

    public ChatRoom(String roomName, List<User> userList) {
        this.roomName = roomName;
        this.userList = userList;
    }

    public ChatRoom() {
    }

    public String getRoomName() {
        return roomName;
    }

    public List<User> getUserList() {
        return userList;
    }


    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomName='" + roomName + '\'' +
                ", userList=" + userList +
                '}'+System.lineSeparator();
    }
}
