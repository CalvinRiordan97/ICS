package Controller;
import java.util.ArrayList;

import Model.Kanban;
import Model.Lane;

public class kanbanController {
	private Kanban k;
	public kanbanController(String name){
		
		k = new Kanban(name,0);
	}
	
	public float incrementID(){
		float id = k.getUniqueID();
		id++;
		return id;
	}
	public boolean addALane(){
		return false;
	}
	public Lane getALane(String name){
		ArrayList <Lane> arr = k.getLaneArray();
		int size = arr.size();
		boolean found = false;
		Lane x = null;
		for (int i=0; i<size && found ==false; i++){
			x = arr.get(i);
			if (x.getName() == name){
				found = true;
			}
		}
		return x;
	}
	public void createDefaultKanban(){
		createLane("backlog");
	}
}
