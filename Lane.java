import java.util.ArrayList;
/**
* Gives the user the ability to add a ticket 
* Remove a ticket
* Find a ticket
*/
public class Lane {
	//The array list for all the tickets
	private ArrayList <Ticket> mainLane = new ArrayList();
	//The array list for all the tickets that are in the local done lane
	private ArrayList <Ticket> subLane = new ArrayList();

	private String name;
	//If the lane is split into having a local done lane
	private boolean split;
	//Is it a backlog lane
	private boolean backlog;
	//Is it a done Lane
	private boolean done;
	//Is it a user generated lane
	private boolean inProgress;

	public Lane(String name){
		this.name = name;
	}
	public boolean addTicketToMain(Ticket t){
		boolean outcome =false;
		if (backlog == true){
			this.mainLane.add(t);
			sortByPriority(mainLane);
			outcome =true;
		}
		return outcome;
	}
	/**
	 * Removes a ticket from the main lane and adds it to the local done 
	 * provided that the lane is already split and the ticket it already in the 
	 * main lane
	 * @param t The ticket that needs to be moved
	 * @return The outcome of the operation if the ticket was moved the outcome is true
	 */
	public boolean addTicketToSub(Ticket t){
		boolean outcome = false;
		/*
		 * What these booleans here are doing is checking to if:
		 * A. The lane already has a local done 
		 * B. If the ticket exists in the main lane array
		 * The reason for performing operation B is to not give the user the ability 
		 * to any ticket to any lane with a split lane it first must exist in the main array
		 */
		if (split == true){
			if (mainLane.contains(t)){
				//Removing the ticket from the main lane
				mainLane.remove(t);
				this.subLane.add(t);
				sortByPriority(subLane);
				outcome = true;
			}
		}
		return outcome;
	}
	/**
	 * Removes a ticket from the main lane
	 * @param t The Ticket you want to remove
	 * @return The outcome of the operation, Will return true if succesful
	 */
	public boolean removeTicketMain(Ticket t){
		boolean outcome = false;
		for (int i=0; i<mainLane.size() && outcome == false; i++){
			if (t.getTicketID() == mainLane.get(i).getTicketID()){
				mainLane.remove(i);
				outcome= true;
			}
		}
		return outcome;
	}
	/**
	 * Removes a ticket from the sub lane
	 * @param t The Ticket you want to remove
	 * @return The outcome of the operation, Will return true if successful
	 */
	public boolean removeTicketSub(Ticket t){
		boolean outcome = false;
		if (split == true){
			for (int i=0; i<mainLane.size() && outcome == false; i++){
				if (t.getTicketID() == mainLane.get(i).getTicketID()){
					mainLane.remove(i);
					outcome= true;
				}
			}
		}
		return outcome;
	}


	
	/**
	 * Gets a ticket and returns it to the user
	 * Will search both local done and main lane
	 * @param ID The ID of the ticket you want to find
	 * @return
	 */
	public Ticket getTicket(int ID){
		boolean found = false;
		Ticket t = null;
		for (int i=0; i<this.mainLane.size() && found == false; i++){
			t = this.mainLane.get(i);
			if (t.getTicketID() == ID){
				found =true;
			}
		}
		if (found == false && split == true){
			for (int i=0; i<this.subLane.size() && found == false; i++){
				t = this.subLane.get(i);
				if (t.getTicketID() == ID){
					found =true;
				}
			}
		}
		if (found == false){
			t =null;
		}
		return t;
	}
	/**
	 * Ensures that the lane is already sorted by the highest priority
	 * @param arr
	 */
	private void sortByPriority(ArrayList<Ticket> arr){
		//@ TODO This definitely needs testing
		int length = arr.size() - 1;
		boolean change = true;
		int z = -1;
		while (change == true) {
			change = false;
			z++;
			for (int i = 0; i < length - z; i++) {
				//Getting a player at the current index and one ahead 
				Ticket x = arr.get(i);
				Ticket y = arr.get(i + 1);

				if (x.getPriority()<y.getPriority()) {
					arr.remove(i);
					arr.remove(i);

					arr.add(i, x);
					arr.add(i + 1, y);
					change = true;
				}
			}
		}
	}
	/**
	 * Will make the lane have a local done lane
	 * Can only be done if it is a user generated lane,
	 * ready, or in progress
	 * 
	 * This will return false if it is already split
	 * or it cannot be spit
	 * @return outcome of operation
	 */
	public boolean setAsLocalDone(){
		boolean outcome = false;
		//Seeing if the lane is not already split
		if (this.split != true){
			//checking if its not a back log lane
			if (this.backlog != true){
				//checking if its not a done lane
				if (this.done != false){

					this.split = true;
					outcome = true;
				}
			}
		}
		return outcome;
	}
}
