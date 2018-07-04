package ua.kiev.prog;


import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
	private static final MessageList msgList = new MessageList();
	private  Map<User, List<Message>> userMap=new HashMap<>();
    private  Gson gson;
	private  List<Message> list = new LinkedList<>();
	
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

	public synchronized  String userPrivateMessToJSON(User user, int n){

		if(userMap.get(user)!=null){
			List<Message> tempList=new ArrayList<>();
			for (int i = n; i < userMap.get(user).size(); i++) {
				tempList.add(userMap.get(user).get(i));
			}
			return  gson.toJson(tempList);
		}

		return null;
	}

	public synchronized void addPrivateMess(Message mess, User toUser){
		List<Message> privateMess=new ArrayList<>();
		privateMess.add(mess);

		if(userMap.containsKey(toUser)){
			List<Message> tempList=userMap.get(toUser);
			tempList.add(mess);
			userMap.put(toUser,tempList);
			return;
		}
		userMap.put(toUser,privateMess);
	}

	public synchronized void addPrivateMess(User toUser){
		List<Message> privateMess=new ArrayList<>();

		if(userMap.isEmpty()){
			userMap.put(toUser,privateMess);
			return;
		}

		if(userMap.containsKey(toUser)){

			return;
		}
		userMap.put(toUser,privateMess);
	}

	public List<User> getUserList(){
		return new ArrayList<User>(userMap.keySet());
	}

	public Map<User, List<Message>> getUserMap() {
		return userMap;
	}
}
