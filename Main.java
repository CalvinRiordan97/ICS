import java.util.ArrayList;
import java.util.HashMap;

import Controller.kanbanController;
import Controller.laneController;
import Model.Kanban;
import Model.Lane;
import Model.Ticket;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application{
	Stage window;
	static kanbanController kc;
	static laneController lc;
	static Kanban kanban;
	public BorderPane layout = new BorderPane();
	public HashMap<String,GridPane> kGrids = new HashMap<String,GridPane>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		kc = new kanbanController(null);
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		window = primaryStage;
		window.setTitle("Kanban Board");

		//Declare the buttons for the menu 
		//Button newKanban = new Button("New Kanban Board");
		Button loadKanban = new Button("load Kanban Board");
		//Button add = new Button("Add Ticket");
		Button move = new Button("Move Ticket");
		Button insert = new Button("Insert Lane");
		Button save = new Button("Save Kanban Board");

		//Create a gridpane 
		//GridPane layout = createGridPane();

		//File Menu
		Menu fileMenu = new Menu("File");
		//Edit Menu
		Menu editMenu = new Menu("Edit");

		//Menu Items
		MenuItem newKanban = new MenuItem("New Kanban Board...");
		fileMenu.getItems().add(newKanban);
		fileMenu.getItems().add(new MenuItem("Load Kanban Baord..."));
		fileMenu.getItems().add(new MenuItem("Save Kanban Board..."));

		MenuItem addTicket = new MenuItem("Add a ticket...");
		editMenu.getItems().add(addTicket);

		//Main Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(editMenu);


		layout.setTop(menuBar);
		//add the buttons to the menu  
		/*layout.add(newKanban, 0, 0);
		layout.add(loadKanban, 1, 0);
		layout.add(add, 10, 0);
		layout.add(move, 11, 0);
		layout.add(insert, 12, 0);
		layout.add(save, 13, 0);*/

		//Create the scene
		Scene scene = new Scene(layout,740,300);
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

			create.setOnAction(e2->{
				kanban = new Kanban(ownerInput.getText());
				kc = new kanbanController(kanban);
				kc.incrementID();
				kc.createDefaultKanban();
				createKanbanBoard();
				//layout.add(wrapper,0,1);
				window.close();
			});
		});

		//when "Add ticket is clicked"
		addTicket.setOnAction(e->{
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
			gp.add(create, 0, 4);
			Scene s = new Scene(gp,400,200);
			window.setScene(s);
			window.show();

			//when the create button is clicked
			create.setOnAction(e2->{
				Ticket newTicket;
				String ticketTask = taskInput.getText();
				newTicket = new Ticket(0,ticketTask,"backlog");
				Lane lane = kc.getALane("Backlog");
				newTicket.setTicketID(kc.incrementID());
				lc = new laneController(lane);
				lc.addATicket(newTicket);

				float y = newTicket.getTicketID();
				Label l = new Label(ticketTask);
				GridPane backlog = kGrids.get("Backlog");
				backlog.add(l,0,(int) y);
				l.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if(mouseEvent.getClickCount() == 2){
							System.out.println("Double clicked");
							String str = l.getText();
							ArrayList<Lane> lanes = kanban.getLaneArray();
							for(int i=0; i <= lanes.size(); i++){
								Lane lane = lanes.get(i);
								HashMap<String,Ticket> tickets = lane.getLaneArr();
								Ticket selected = tickets.get(str);
								if(selected != null){
									kc.moveATicket(selected);
									System.out.println("ayy");
									break;
								}
							}
						}
					}
				});
				window.close();
			});
		});

		move.setOnAction(e->{
			window = new Stage();
			window.setTitle("Move Ticket");
			Label label = new Label("Lane:");
			ArrayList<Lane> arr = kanban.getLaneArray();
			ArrayList<String> lanes = new ArrayList<String>();
			for(int i=0; i<arr.size(); i++){
				Lane l = arr.get(i);
				String name = l.getName();
				lanes.add(name);
			}

			//Choice box for the lanes
			ChoiceBox<String> laneSelect = new ChoiceBox<String>();
			laneSelect = new ChoiceBox<String>(FXCollections.observableArrayList(lanes));
			String choice = lanes.get(0);
			laneSelect.setValue(lanes.get(0));

			//Choice box for the tickets
			ChoiceBox<String> ticketSelect = new ChoiceBox<String>();
			laneSelect = new ChoiceBox<String>(FXCollections.observableArrayList(lanes));
			String choice2 = lanes.get(0);
			laneSelect.setValue(lanes.get(0));

			Button moveTicket = new Button("Move Ticket");
			GridPane gp = createGridPane();
			gp.add(label, 0, 2);
			gp.add(laneSelect, 1, 2);
			gp.add(moveTicket, 0, 4);

			Scene s = new Scene(gp,400,200);
			window.setScene(s);
			window.show();

			laneSelect.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {

			});

			moveTicket.setOnAction(e2->{
				//int id = Integer.parseInt(input.getText());
			});


		});

		//when "insert lane" is clicked
		insert.setOnAction(e->{
			window = new Stage();
			window.setTitle("Insert Lane");

			Label laneName = new Label("Lane Name: ");
			TextField nameInput = new TextField();

			Label lanePos= new Label("Lane Position: ");
			TextField posInput = new TextField();

			Button create = new Button("Insert Lane");

			GridPane gp = createGridPane();
			gp.add(laneName, 0, 2);
			gp.add(nameInput, 1, 2);
			gp.add(lanePos, 0, 4);
			gp.add(posInput, 1, 4);
			gp.add(create, 0, 5);

			Scene s = new Scene(gp,400,200);
			window.setScene(s);
			window.show();

			create.setOnAction(e2->{
				String name = nameInput.getText();
				int position = Integer.parseInt(posInput.getText());
				boolean validate = kc.createAUserLane(name, position);

				if (validate == false){
					Alert alert = new Alert(AlertType.ERROR, "You have entered an inavalid name or position");
					alert.show();
				}
				else{
					window.close();
				}
				//Lane l = new Lane(); <- What's the point of having 
				//a lane class if the lanes re made in the kanbanController?
			});
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

	private void createKanbanBoard(){
		ArrayList<Lane> lanes = kanban.getLaneArray();
		GridPane x = createGridPane();
		layout.setCenter(x);
		for(int i=0; i<lanes.size(); i++){
			Lane l = lanes.get(i);
			String str = l.getName();
			Label laneName = new Label(str);
			GridPane kGrid = createGridPane();
			kGrid.add(laneName,0,0);
			x.add(kGrid,i,0);
			kGrids.put(str,kGrid);
		}
	}
}
