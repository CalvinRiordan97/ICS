package Controller;
import java.util.ArrayList;
import Model.Kanban;
import Model.Lane;
/**
 * @version  1.0
 * @todo Add functions to move tickets to the lane to the left
 */
public class kanbanController {
	private Kanban kanbanBoard;
	
	public kanbanController(Kanban bored){
		kanbanBoard = bored;
	}
	/**
	 * Increments the ID and returns an Id to the user 
	 * this is used whenever creating a ticket
	 * @return
	 */
	// @ TODO test
	public void incrementID(){
		float id = kanbanBoard.getUniqueID();
		id++;
		kanbanBoard.setUniqueID(id);
	}
	
	/**
	 * Gets a lane with a given name
	 * @param name The name of the lane you want to get
	 * @return A lane
	 */
	public Lane getALane(String name){
		ArrayList <Lane> arr = kanbanBoard.getLaneArray();
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
		ArrayList <Lane> arr = kanbanBoard.getLaneArray();
		Lane A = new Lane ();
		Lane B = new Lane ();
		Lane C = new Lane ();
		Lane D = new Lane ();
		LaneController controlLane = new LaneController(A);
		controlLane.createLane("backlog");
		arr.add(A);
		controlLane.changeControlTo(B);
		controlLane.createLane("ready");
		arr.add(B);
		controlLane.changeControlTo(C);
		controlLane.createLane("in progress");
		arr.add(C);
		controlLane.changeControlTo(D);
		controlLane.createLane("done");
		arr.add(D);
		kanbanBoard.setLaneArray(arr);
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
	// @ TODO test
	public boolean createAUserLane(String name, int pos){
		boolean outcome = false;
		boolean badName = false;
		ArrayList<Lane> arr = kanbanBoard.getLaneArray();
		for (int i=0; i<arr.size() && badName == false; i++){
			Lane check = arr.get(i);
			if (check.getName().toLowerCase().equals(name.toLowerCase())){
				badName = true;
				outcome = false; 
			}
		}
		if (pos  > 0 || pos >= arr.size()-1){
			Lane adderLane = new Lane();
			arr.add(pos, adderLane);
			kanbanBoard.setLaneArray(arr);
			outcome = true;
		}
		else{
			outcome = false;
		}

		return outcome;
	}
	/**
	 * Removes a lane by the name provided its a user created lane
	 * @param x
	 * @return
	 */
	// @ TODO test
	public boolean removeALane(Lane x){
		boolean outcome = false;
		ArrayList <Lane> arr = kanbanBoard.getLaneArray();
		for (int i=0; i<arr.size() && outcome == false; i++){
			Lane l = arr.get(i);
			if (l.getBooleanArr()[2] == true){
				if (l.getLaneArr().equals(x.getName())){
					arr.remove(i);
					outcome = true;
				}
			}
		}
		return outcome;
	}
}
