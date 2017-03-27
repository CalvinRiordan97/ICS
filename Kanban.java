package phase3;

import java.util.ArrayList;

public class Kanban{
	private float laneID=0;
	private String kanbanName; 
	private int priority =0;
	
	private ArrayList <Lane> lanes = new ArrayList<Lane>();
	Lane backlog = new Lane("Backlog", 0);
	Lane ready = new Lane("Ready", 1);
	Lane inProgress = new Lane("In Progress", 2);
	Lane done = new Lane("Done", 3);
	Kanban (int id, String tn){
		this.laneID = id;
		this.kanbanName=tn;
		this.priority =0;
	}
	/**
	 * 
	 * @param id
	 * @param tn
	 * @param priority
	 */
	Kanban(int id, String tn/*, int priority*/){
		this.laneID = id;
		this.kanbanName=tn;
		//this.priority = priority;
	}
	
	
	public float getLaneID() {
		return laneID;
	}
	public void setLaneID(float laneID) {
		this.laneID = laneID;
	}
	public String getKanbanName() {
		return kanbanName;
	}
	public void setKanbanName(String kanbanName) {
		this.kanbanName = kanbanName;
	}
	

	
}
