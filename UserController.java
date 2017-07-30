package Controller;
import Model.User;

import java.util.HashMap;

import Model.Kanban;

public class UserController {
	private User user;

	public UserController (User user){
		this.user = user;
	}
	public void createProductOwner(String name, String password, float ID){
			String id = String.valueOf(ID);
			String userName = name+user.PRODUCT_OWNER+Float.valueOf(id);
			user.setUserName(userName);
			user.setPassword(password);
			user.setOwner(true);
			System.out.println("New User Created " + userName);
			System.out.println("Password: "+password);
			System.out.println("Is owner "+user.isOwner());
	}
	/**
	 * Creates a software devloper User;
	 * @param name
	 * @param password
	 * @param ID
	 */
	public void createSoftwareDev(String name, String password, float ID){	
			String id = String.valueOf(ID);
			String userName = name+"DEV"+id;
			user.setUserName(userName);
			user.setPassword(password);
			user.setOwner(false);
			System.out.println("New User Created " + userName);
	}
	
	public boolean UserExisits(HashMap <String,User> list, String name, String password){
		boolean outcome = false;
		User user = list.get(name);
		if (user != null){
			if (user.getPassword().equals(password)){
				outcome = true;
			}
		}
		return outcome;
	}
}
