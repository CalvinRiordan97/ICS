package phase3;

public class Ticket {
	private float ticketID=0;
	private String ticketName;
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
	Ticket(int id, String tn){
		this.ticketID = id;
		this.ticketName=tn;
		this.priority =0;
		this.blockedStatus = false;
	}
	/**
	 * 
	 * @param id
	 * @param tn
	 * @param priority
	 */
	Ticket(int id, String tn, int priority){
		this.ticketID = id;
		this.ticketName=tn;
		this.priority = priority;
		this.blockedStatus = false;
	}

	public float getTicketID() {
		return ticketID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
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
