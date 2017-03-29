package Controller;
import java.util.ArrayList;
import Model.Kanban;
import Model.Lane;
/**
 * @version  1.0
 */
public class kanbanController {
	private ArrayList <Lane> arr; 
	private Kanban k;
	private LaneController laneC;
	public kanbanController(String name){

		k = new Kanban(name,0);
		laneC = new LaneController("");
		arr = new ArrayList <Lane>();
	}
	/**
	 * Increments the ID and returns an Id to the user 
	 * this is used whenever creating a ticket
	 * @return
	 */
	public float incrementID(){
		float id = k.getUniqueID();
		id++;
		return id;
	}
	
	/**
	 * Gets a lane with a given name
	 * @param name The name of the lane you want to get
	 * @return A lane
	 */
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
	/**
	 * Creates a default kanban with four lanes
	 */
	public void createDefaultKanban(){
		arr.add(laneC.createLane("backlog"));
		arr.add(laneC.createLane("ready"));
		arr.add(laneC.createLane("in progress"));
		arr.add(laneC.createLane("done"));
	}
	/**
	 * Creates a user generated lane at a given point
	 * provided it does not have the same name as another a lane
	 * and provided it does not have a position less than zero or greater the the final 
	 * element
	 * @param name The name of the lane your going to add
	 * @param pos Where you want to place it
	 * @return if outcome false operation failed
	 */
	public boolean createAUserLane(String name, int pos){
		boolean outcome = false;
		boolean badName = false;
		for (int i=0; i<arr.size() && badName == false; i++){
			Lane check = arr.get(i);
			if (check.getName().toLowerCase().equals(name.toLowerCase())){
				badName = true;
				outcome = false; 
			}
		}
		if (pos  > 0 || pos >= arr.size()-1){
			Lane adderLane = new Lane(name);
			arr.add(pos, adderLane);
		}
		else{
			outcome = false;
		}

		return outcome;
	}
}
