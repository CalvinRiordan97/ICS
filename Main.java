import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application{
	Stage window;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		window = primaryStage;
		window.setTitle("Kanban Board");

		//Declare the buttons for the menu 
		Button newKanban = new Button("New Kanban Board");
		Button loadKanban = new Button("load Kanban Board");
		Button add = new Button("Add Ticket");
		Button insert = new Button("Insert Lane");
		Button save = new Button("Save Kanban Board");

		//Create a gridpane 
		GridPane layout = createGridPane();

		//add the buttons to the menu  
		layout.add(newKanban, 0, 0);
		layout.add(loadKanban, 1, 0);
		layout.add(add, 10, 0);
		layout.add(insert, 11, 0);
		layout.add(save, 12, 0);

		//Create the scene
		Scene scene = new Scene(layout,650,300);
		window.setScene(scene);
		window.show();

		// when "New kanban Board" is clicked
		newKanban.setOnAction(e-> {
			window = new Stage();
			window.setTitle("New kanban Board");

			Label owner = new Label("Kanban Board Creator:");
			TextField ownerInput = new TextField();

			String[] choice = new String[]{"a","b","c"};
			Label colour = new Label("Select Colour:");
			ChoiceBox colourSelect = new ChoiceBox(FXCollections.observableArrayList("White", "Green", "Blue"));

			Button create = new Button("Create Kanban Board");

			GridPane gp = createGridPane();

			gp.add(owner, 0, 2);
			gp.add(ownerInput, 1, 2);
			gp.add(colour, 0, 4);
			gp.add(colourSelect, 1, 4);
			gp.add(create, 0, 6);

			Scene s = new Scene(gp,400,200);
			window.setScene(s);
			window.show();
		});

		//when "Add ticket is clicked"
		add.setOnAction(e->{
			window = new Stage();
			window.setTitle("Add Ticket");
			
			//Create a label called task
			Label task = new Label("Task: ");
			//Create a textfield called input
			TextField taskInput = new TextField();
			
			//CheckBox blocked = new CheckBox("Blocked");
			//blocked.setSelected(false);
			
			//Create a button that when clicked creates the ticket
			Button create = new Button("Add Ticket");
			
			//Create the grid pane
			GridPane gp = createGridPane();
			gp.add(task, 0, 2);
			gp.add(taskInput, 1, 2);
			//gp.add(blocked, 0, 6);
			gp.add(create, 0, 4);
			
			
			Scene s = new Scene(gp,400,200);
			window.setScene(s);
			window.show();
			
			//when the create button is clicked
			create.setOnAction(e2->{
				Ticket newTicket;
				//String name = nameInput.getText();
				String ticketTask = taskInput.getText();
				//boolean x = blocked.isSelected();
				newTicket = new Ticket(0,ticketTask);
			});
		});
		
		//when "insert lane" is clicked
		insert.setOnAction(e->{
			window = new Stage();
			window.setTitle("Insert Lane");
			
			Label laneName = new Label("Lane Name: ");
			TextField nameInput = new TextField();
			
			Button create = new Button("Insert Lane");
			
			GridPane gp = createGridPane();
			gp.add(laneName, 0, 2);
			gp.add(nameInput, 1, 2);
			gp.add(create, 0, 4);
			
			Scene s = new Scene(gp,400,100);
			window.setScene(s);
			window.show();
		});
	}

	//This method create a grid pane and sets the padding,VGap and HGap, and returns it
	private GridPane createGridPane(){
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(8);
		gp.setHgap(10);
		return gp;
	}
}
