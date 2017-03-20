import java.util.ArrayList;
public class Lane {
	private ArrayList <Ticket> arr = new ArrayList();
	private String name;

	public Lane(String name){
		this.name = name;
	}
	public void addTicket(Ticket t){
		arr.add(t);
		sortByPriority();
	}
	public Ticket getTicket(int ID){
		boolean found = false;
		Ticket t = null;
		for (int i=0; i<arr.size() && found == false; i++){
			t = arr.get(i);
			if (t.getTicketID() == ID){
				found =true;
			}
		}
		return t;
	}
	private void sortByPriority(){
		int length = this.arr.size() - 1;
		boolean change = true;

		int z = -1;
		while (change == true) {
			change = false;
			z++;

			for (int i = 0; i < length - z; i++) {
				//Getting a player at the current index and one ahead 
				Ticket x = this.arr.get(i);
				Ticket y = this.arr.get(i + 1);

				if (x.getPriority()>y.getPriority()) {
					this.arr.remove(i);
					this.arr.remove(i);

					this.arr.add(i, y);
					this.arr.add(i + 1, x);
					change = true;
				}
			}
		}
	}
}
