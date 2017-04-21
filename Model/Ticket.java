package Model;
public class Ticket {
	private float ticketID;
	private String ticketTask;
	private String ticketLane;
	private int priority;
	//True means blocked
	private boolean blockedStatus;
	/**
	 * Priority is defaulted to 0
	 * blocked status is false
	 * @param id The UNIQUE id of the ticket
	 * @param tn The name of the ticket
	 */

	/**
	 * 	 * @param id
	 * @param tn
	 */
	public Ticket(float id, String tn, String l){
		this.ticketID = id;
		this.ticketTask=tn;
		this.ticketLane=l; // I added this so the ticket knows what lane its in. This will save time probably 
		this.priority =0;
		this.blockedStatus = false;
	}
	/**
	 * 
	 * @param id
	 * @param tn
	 * @param priority
	 */
	Ticket(int id, String tn, String l, int priority){
		this.ticketID = id;
		this.ticketTask=tn;
		this.ticketLane=l;
		this.priority = priority;
		this.blockedStatus = false;
	}

	public float getTicketID() {
		return ticketID;
	}

	public void setTicketID(float ticketID) {
		this.ticketID = ticketID;
	}

	public String getTicketName() {
		return ticketTask;
	}

	public void setTicketName(String ticketName) {
		this.ticketTask = ticketName;
	}

	public String getTicketLane(){
		return ticketLane;
	}
	
	public void setTicketLane(String ticketLane){
		this.ticketLane = ticketLane;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isBlockedStatus() {
		return blockedStatus;
	}

	public void setBlockedStatus(boolean blockedStatus) {
		this.blockedStatus = blockedStatus;
	}


}