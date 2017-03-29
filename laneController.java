package Controller;
import java.util.ArrayList;

import Model.Lane;
import Model.Ticket;

public class laneController {
	private Lane l;
	
	public laneController(String name){
		l = new Lane(name);
	}
	
	public boolean [] createUserGeneratedLane(String name){
		boolean permission[] = l.getBooleanArr();
		l.setName(name);
		permission[0] = false;
		permission[1] = true;
		permission[2] = true;
		return permission;
	}
	
	public  boolean [] createDone(){
		boolean permission[] = l.getBooleanArr();
		l.setName("Done");
		permission[0] = false;
		permission[1] = false;
		permission[2] = true;
		return permission;
	}
	/**
	 * If a user enters Backlog a backlog lane will be created 
	 * @param name
	 * @return
	 */
	public Lane createLane(String name){
		Lane x= null;
		if (name.equals("backlog")){
			x = new Lane(name);
			boolean permission[] = x.getBooleanArr();
			permission[0] = true;
			permission[1] = false;
			permission[2] = false;
			x.setBooleanArr(permission);
		}
		else if (name.equals("ready")){
			x = new Lane(name);
			boolean permission[] = x.getBooleanArr();
			permission[0] = false;
			permission[1] = false;
			permission[2] = true;
			x.setBooleanArr(permission);
		}
		else if(name.equals("in progress")){
			boolean permission[] = x.getBooleanArr();
			x.setName("In Progress");
			permission[0] = false;
			permission[1] = false;
			permission[2] = true;
			x.setBooleanArr(permission);
		}
		else if (name.equals("done")){
			boolean permission[] = x.getBooleanArr();
			x.setName("Done");
			permission[0] = false;
			permission[1] = false;
			permission[2] = true;
			x.setBooleanArr(permission);
		}
		else {
			boolean permission[] = x.getBooleanArr();
			x.setName(name);
			permission[0] = false;
			permission[1] = true;
			permission[2] = true;
			x.setBooleanArr(permission);

		}
		
		return x;	
	}
	public void sortByPriority(){
		//bubble sort
		ArrayList<Ticket> arr = l.getLaneArr();
		int length = arr.size()- 1;
		boolean change = true;
		
		int z = -1;
		while (change == true) {
			change = false;
			z++;
		
			for (int i = 0; i < length - z; i++) {
				//Getting a player at the current index and one ahead 
				Ticket x = arr.get(i);
				Ticket y = arr.get(i + 1);

				if (x.compareTo(y)) {
					arr.remove(i);
					arr.remove(i);

					arr.add(i, y);
					arr.add((i + 1, x);
					change = true;
				}
			}
		}
	}
}
/*
 *  1. Add a Ticket 
 *  2. Change Name
 *  3. Split Lane
 */