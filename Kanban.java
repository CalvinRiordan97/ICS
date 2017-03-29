package Model;
import java.util.ArrayList;
public class Kanban {
	private float uniqueID;
	private String name;
	private ArrayList <Lane> arr;
	
	public Kanban(String name, float i) {
		this.name = name;
		uniqueID = i;
		arr = new ArrayList<Lane>();
	}
	public float getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(float uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Lane> getLaneArray(){
		return arr;
		
	}
	

}
