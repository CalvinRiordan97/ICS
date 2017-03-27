package Model;

import java.util.ArrayList;

public class Lane {
	//The array list for all the tickets
		private ArrayList <Ticket> LaneArr = new ArrayList<Ticket>();
		
		private String name;
		
		private boolean booleanArr [];
		private final int NUM_BOOLEANS = 3;
		
		public Lane(String name){
			this.name = name;
			booleanArr = new boolean[6];
		}
		public ArrayList<Ticket> getLaneArr() {
			return LaneArr;
		}
		public void setLaneArr(ArrayList<Ticket> laneArr) {
			LaneArr = laneArr;
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
		
		
}
