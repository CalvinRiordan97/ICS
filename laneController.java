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
	
	/*public  boolean [] createDone(){
		boolean permission[] = l.getBooleanArr();
		l.setName("Done");
		permission[0] = false;
		permission[1] = false;
		permission[2] = true;
		return permission;
	}*/
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
	public Lane sortByPriority(Lane l){
		//bubble sort
		Ticket t1,t2; //tickets
		int p1,p2; //priority
		boolean swapped;

		do{
			swapped = false;
			for(int i=0; i<l.getLaneArr().size()-1; i++){
				t1 = l.getLaneArr().get(i);
				p1 = t1.getPriority();

				t2 = l.getLaneArr().get(i+1);
				p2 = t2.getPriority();

				if(p1>p2){
					l.getLaneArr().remove(i);
					l.getLaneArr().add(i, t1);
					l.getLaneArr().remove(i+1);
					l.getLaneArr().add(i+1, t2);
					swapped = true;
				}
			}
		}while(swapped == true);
		return l;
	}
}
/*
 *  1. Add a Ticket 
 *  2. Change Name
 *  3. Split Lane
 */
