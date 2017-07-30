package Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * 
 * @version 1
 *
 */
public class Kanban implements Serializable{
	private float uniqueID;
	private String name;
	private ArrayList <Lane> laneArray;
	private ArrayList <User> userList;
	private User currentUser;
	public Kanban(String name) {
		this.name = name;
		uniqueID = 0;
		laneArray = new ArrayList <Lane>();
		userList = new ArrayList<User>();
	}
	public float getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(float uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Lane> getLaneArray(){
		return laneArray;
	}
	public void setLaneArray(ArrayList<Lane> arr){
		this.laneArray = arr;
	}
	public ArrayList<User> getUserArray(){
		return userList;
	}
	public void setUserArray(ArrayList<User> list){
		this.userList = list;
	}
	public void setCurrentUser(User user){
		currentUser = user;
	}
	public User getCurrentUser(){
		return currentUser;
	}
	public User findAUser(User x){
		User user = null;
		userList.indexOf(x);
		return user;
	}
}