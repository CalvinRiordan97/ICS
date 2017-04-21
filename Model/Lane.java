package Model;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * @author Version 1.5
 * Has SubLane Function
 */
public class Lane {
	//The array list for all the tickets
		private HashMap <String,Ticket> tickets; 
		private ArrayList <Ticket> SubLaneTickets; 
		private String name;
		/*
		 * 1. Can make a sub Lane
		 * 2. Can edit the the name after created 
		 * 3. Can add a ticket
		 * 4. The Lane HAS a sub
		 */
		private boolean booleanArr [];
		private final int NUM_BOOLEANS = 4;
		
		public Lane(){
			tickets = new HashMap<String,Ticket>();
			SubLaneTickets= new ArrayList<Ticket>();
			booleanArr = new boolean[NUM_BOOLEANS];
		}
		public HashMap<String,Ticket> getLaneArr() {
			return tickets;
		}
		public void setLaneArr(HashMap<String, Ticket> laneArr) {
			tickets = laneArr;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean[] getBooleanArr() {
			return booleanArr;
		}
		public void setBooleanArr(boolean[] booleanArr) {
			this.booleanArr = booleanArr;
		}
		public ArrayList<Ticket> getSubLaneArr() {
			return SubLaneTickets;
		}
		public void setSubLaneArr(ArrayList<Ticket> sublaneArr) {
			SubLaneTickets = sublaneArr;
		}
		
}