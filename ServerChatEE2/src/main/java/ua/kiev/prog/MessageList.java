package ua.kiev.prog;


import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
    private static final MessageList msgList = new MessageList();
    private Map<User, List<Message>> userMap = new HashMap<>();
    private Gson gson;
    private List<Message> list = new LinkedList<>();

    public static MessageList getInstance() {
        return msgList;
    }

    private MessageList() {
        gson = new GsonBuilder().create();
    }

    public synchronized void add(Message m) {
        list.add(m);
    }

    public synchronized String toJSON(int n) {
        if (n >= list.size()) return null;
        return gson.toJson(new JsonMessages(list, n));
    }

    public synchronized String userPrivateMessToJSON(int userHash, int n) {
        User user = new User();
        Set<User> keySet = userMap.keySet();

        for (User us : keySet) {
            if (us.hashCode() == userHash) {
                user = us;
            }
        }

        if (userMap.containsKey(user) && userMap.get(user) != null) {
            List<Message> tempList = new ArrayList<>();
            for (int i = n; i < userMap.get(user).size(); i++) {
                tempList.add(userMap.get(user).get(i));
            }
            return gson.toJson(tempList);
        }

        return null;
    }

    public synchronized void addPrivateMess(Message mess, User toUser) {

        List<Message> privateMess = new ArrayList<>();
        privateMess.add(mess);

        if (userMap.containsKey(toUser)) {
            List<Message> tempList = userMap.get(toUser);
            tempList.add(mess);
            userMap.put(toUser, tempList);
//            System.out.println(userMap+"from add private mess");
            return;
        }
        userMap.put(toUser, privateMess);
//        System.out.println(userMap+"from add private mess");
    }

    public synchronized void addPrivateMess(User toUser) {
        List<Message> privateMess = new ArrayList<>();

        if (userMap.isEmpty()) {
            userMap.put(toUser, privateMess);
            return;
        }

        if (userMap.containsKey(toUser)) {

            return;
        }
        userMap.put(toUser, privateMess);
//        System.out.println(userMap+"from add user");
    }

    public synchronized List<User> getUserList() {
        return new ArrayList<User>(userMap.keySet());
    }

    public synchronized Map<User, List<Message>> getUserMap() {
        return userMap;
    }
}
