
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import Controller.KanbanController;
import Controller.LaneController;
import Controller.UserController;
import Model.Kanban;
import Model.Lane;
import Model.Ticket;
import Model.User;
import View.UserView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application{
	Stage window;
	static KanbanController kc;
	static LaneController lc;
	static Kanban kanban;
	public BorderPane layout = new BorderPane();
	public static GridPane mainGrid;
	//Height and width of tickets
	final int TICKET_HEIGHT = 150;
	final static int TICKET_WIDTH = 150;
	public boolean alreadyLoaded = false;

	public static void main(String[] args) {
		kc = new KanbanController(null);
		//Creating the main Grid Pane
		mainGrid = new GridPane();
		mainGrid.setPadding(new Insets(0,TICKET_WIDTH,0,TICKET_WIDTH));
		mainGrid.setId("ggg");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		Scene s = userView.splashPage();
		 */
		GridPane gp = createGridPane();
		Text title = new Text("Please login to an exsiting Kanban or create a new one");
		gp.add(title, 0, 1);

		Button login = new Button("Login To Existing");
		gp.add(login, 0, 3);
		login.setOnAction(e->{
			window.close();
			loginAUser();
		});

		Button newBoard = new Button("Create New");
		gp.add(newBoard, 1, 3);
		newBoard.setOnAction(ex->{
			window.close();
			Kanban kanban1 = new Kanban("");
			KanbanController kc = new KanbanController(kanban1);
			kc.createDefaultKanban();

			newKanban(kanban1);
		});
		window = primaryStage;
		Scene s = new Scene(gp);
		window.setTitle("Welcome");
		window.setScene(s);
		window.show();

	}

	private void newKanban(Kanban k){
		BorderPane layout = new BorderPane();
		GridPane gp = createGridPane();
		Scene s = new Scene(layout, 400, 200);
		Stage x = new Stage();

		Text title = new Text("\n"
				+ "   Please enter your details so we can create an account for you");
		layout.setTop(title);

		Label nameLabel = new Label("Name");
		gp.add(nameLabel, 0,0);

		TextField nameInput = new TextField();
		gp.add(nameInput, 1, 0);

		Label passwordLabel = new Label("Password");
		gp.add(passwordLabel, 0, 2);

		TextField passwordInput = new TextField();
		gp.add(passwordInput, 1, 2);

		Button enterDetails = new Button("Create account");
		gp.add(enterDetails, 1, 3);
		layout.setCenter(gp);
		enterDetails.setOnAction(e->{
			User user = new User("","");
			String name = nameInput.getText();
			String password = passwordInput.getText();
			if (name.length()> 0 && password.length()>0){
				kc = new KanbanController(k);
				UserController uc = new UserController(user);
				uc.createProductOwner(name, password, kc.incrementID());
				kanban = k; 
				kc = new KanbanController(kanban);
				kc.loginUser(user);
				ArrayList<User> ki =kanban.getUserArray();
				ki.add(user);
				kanban.setUserArray(ki);
				//kc.addUserToArray(user);
				x.close();
				Alert alert = new Alert(AlertType.INFORMATION, "User name: "+user.getUserName()
				+"\nPassword: "+user.getPassword());
				showKanban();
				alert.show();

			}else{
				Alert alert = new Alert(AlertType.ERROR,"One or more Fields has no content");
				alert.show();
			}
		});
		x.setScene(s);
		x.show();
	}

	private void loginAUser(){
		Stage s = new Stage();
		BorderPane bp = new BorderPane();
		Text title = new Text("Please enter your login details.");
		bp.setPadding(new Insets(10,10,10,10));
		bp.setTop(title);
		GridPane gp = createGridPane();
		//User name
		Label userNamelLabel = new Label("User Name");
		gp.add(userNamelLabel, 0, 0);
		TextField userNameInput = new TextField();
		gp.add(userNameInput, 1, 0);

		//Password
		Label passwordLabel = new Label("Password");
		gp.add(passwordLabel, 0, 1);
		TextField passwordInput = new TextField();
		gp.add(passwordInput, 1, 1);

		Button login = new Button("login");
		gp.add(login, 1, 2);
		bp.setCenter(gp);
		login.setOnAction(e->{
			String userName = userNameInput.getText();
			String password = passwordInput.getText(); 
			if (userName.length()>0 && password.length()>0){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				/*
				 *Default path here is where the testing folder is  
				 */
				//fileChooser.setInitialDirectory(new File("C:\\Users\\chris\\Documents\\College\\Eclipse\\ICS\\Saves"));
				File file = fileChooser.showOpenDialog(window);
				Kanban x = null;
				try {
					FileInputStream fileIn = new FileInputStream(file);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					x = (Kanban) in.readObject();
					in.close();
					fileIn.close();
				}catch(IOException i) {
					i.printStackTrace();
					return;
				}catch(ClassNotFoundException c) {
					c.printStackTrace();
					return;
				}
				kc = new KanbanController(x);
				User user = kc.findUserByID(userName);
				if (user != null){
					if (user.getPassword().equals(password)){
						System.out.println("User Found");
						kanban = x;
						kc = new KanbanController(kanban);
						// @ TODO
						kc.loginUser(user);
						if (alreadyLoaded == false){
							showKanban();
						}else{
							updateBoard();
						}
						s.close();
					}else{
						Alert alert = new Alert(AlertType.ERROR,"Incorrect Password");
						alert.show();
					}
				}else{
					Alert alert = new Alert(AlertType.ERROR, "Either you have entered a bad username or there is no accounant\n"
							+ "\nby the user: "+userName+" on this kanban");
					alert.show();
				}	
			}

		});
		Scene scene  = new Scene(bp, 400,180);
		s.setScene(scene);
		s.show();
	}
	/**
	 * Uses an file chooser to get a kanban from the 
	 * "Saves" directory
	 * @return a file
	 */
	public Kanban getAKanban(){
		Stage primaryStage = new Stage();
		Window window = primaryStage;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File("C:\\Users\\chris\\Documents\\College\\Eclipse\\ICS\\Saves"));
		File file = fileChooser.showOpenDialog(window);
		//Loading in a file
		Kanban kanban = new Kanban("");
		KanbanController kc = new KanbanController(kanban);
		kc.loadIn(file);
		return kanban;
	}

	private void showKanban(){
		alreadyLoaded = true;

		Stage stage = new Stage();
		window.setTitle("Kanban Board");
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(null);
		mainGrid.getChildren().clear();
		scroller.setContent(mainGrid);
		layout.setCenter(scroller);
		layout.setLeft(CreateTicketWindow());

		//File Menu
		Menu fileMenu = new Menu("File");
		//User MEnu
		Menu user = new Menu("User");
		MenuItem addUser = new MenuItem("Add a User");
		MenuItem loginUser = new MenuItem("Login A User");
		user.getItems().add(loginUser);
		user.getItems().add(addUser);
		
		addUser.setOnAction(q ->{
			createDev();
		});
		loginUser.setOnAction(ty ->{
			loginAUser();
		});
		//Menu Items
		MenuItem save = new MenuItem("Save Kanban Board...");
		MenuItem load =  new MenuItem("Load Kanban Board");

		fileMenu.getItems().add(load);
		load.setOnAction(loader ->{
			loginAUser();
		});
		fileMenu.getItems().add(save);
		save.setOnAction(saver -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save File");
			fileChooser.setInitialDirectory(new File("C:\\Users\\chris\\Documents\\College\\Eclipse\\ICS\\Saves"));
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("SER", "*.ser")
					);
			File file = fileChooser.showSaveDialog(window);
			kc.saveKanban(file);
		});


		//Edit Menu
		Menu editMenu = new Menu("Edit");
		//Adding sub items to this menu
		MenuItem addTicket = new MenuItem("Add a ticket...");
		MenuItem createNewLane = new MenuItem("Create New Lane");
		MenuItem deleteALane = new MenuItem("Delete A Lane");
		MenuItem splitLane = new MenuItem("Split A Lane");
		MenuItem deleteSubLane = new MenuItem("Delete A Local Done Lane");
		editMenu.getItems().add(addTicket);
		editMenu.getItems().add(createNewLane);
		editMenu.getItems().add(deleteALane);
		editMenu.getItems().add(splitLane);
		editMenu.getItems().add(deleteSubLane);

		//Main Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(editMenu);
		menuBar.getMenus().add(user);
		//Insering this into the layout
		layout.setTop(menuBar);

		//Create the scene
		Scene scene = new Scene(layout,740,300);
		scene.getStylesheets().add("CSS.css");
		stage.setScene(scene);
		stage.show();

		//when "Add ticket is clicked"
		addTicket.setOnAction(e->{
			window = new Stage();
			window.setTitle("Add Ticket");

			//Create a label called task
			Label task = new Label("Task: ");
			//Create a textfield called input
			TextField taskInput = new TextField();
			//Create a button that when clicked creates the ticket
			Button create = new Button("Add Ticket");

			//Create the grid pane
			GridPane gp = createGridPane();
			gp.add(task, 0, 2);
			gp.add(taskInput, 1, 2);
			gp.add(create, 0, 4);
			updateBoard();
			Scene s = new Scene(gp,400,200);
			window.setScene(s);
			window.show();

			//when the create ticket button is clicked
			create.setOnAction(e2->{
				Ticket newTicket;
				String ticketTask = taskInput.getText();
				newTicket = new Ticket(0,ticketTask);
				kc = new KanbanController(kanban);
				Lane lane = kc.getALane("Backlog");
				newTicket.setTicketID(kc.incrementID());
				lc = new LaneController(lane);
				lc.addATicket(newTicket);
				updateBoard();
				window.close();
			});
		});


		//when "insert lane" is clicked
		createNewLane.setOnAction(e->{
			createNewLane();
		});
		deleteALane.setOnAction(l4->{
			deleteALane();
		});
		splitLane.setOnAction(er ->{
			createSubLane();
		});
		deleteSubLane.setOnAction(op -> {
			deleteSubLane();
		});
		updateBoard();
	}	

	private void createDev() {
		window = new Stage();
		window.setTitle("New User");

		Label owner = new Label("Devloper Name");
		TextField ownerInput = new TextField();

		Label passwordL = new Label("New Password");
		TextField passwordIn = new TextField();

		Button create = new Button("Create New User");
		create.setOnAction(e_->{
			String name = ownerInput.getText();
			String password = passwordIn.getText();
			if (name.length()<0 && password.length()<0){
				Alert alert = new Alert(AlertType.ERROR, "One or more fields has not been filled");
				alert.show();
			}else{
				kc = new KanbanController(kanban);
				User user = new User("","");
				UserController userC = new UserController(user);
				userC.createSoftwareDev(name, password, kc.incrementID());
				kanban.getUserArray().add(user);
				Alert alert = new Alert(AlertType.INFORMATION, "Your user-name is "+user.getUserName()
				+"\nYour password is "+user.getPassword());
				alert.show();
				window.close();
			}
		});
		GridPane gp = createGridPane();
		gp.setId("grid");
		gp.add(owner, 0, 2);
		gp.add(ownerInput, 1, 2);
		gp.add(passwordL, 0, 3);
		gp.add(passwordIn, 1, 3);
		gp.add(create, 0, 6);

		Scene s = new Scene(gp,400,200);
		window.setScene(s);
		window.show();

	}

	private static GridPane createGridPane(){
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(15);
		gp.setHgap(10);
		return gp;
	}
	
	private StackPane createTicket(Ticket t){
		//Creating the textObject for the task
		Text task = new Text(t.getTicketName());
		//Sets its max length
		task.setWrappingWidth(TICKET_WIDTH);

		///////////////////////////////////
		////Backing for the rectangles////
		/////////////////////////////////
		Rectangle baseBox = new Rectangle();
		baseBox.setHeight(TICKET_HEIGHT+10);
		baseBox.setWidth(TICKET_WIDTH+10);
		baseBox.setFill(Color.WHITE);

		////////////////////////////
		///Making the Sticky note//
		//////////////////////////
		Rectangle tickBox = new Rectangle();
		tickBox.setHeight(TICKET_HEIGHT);
		tickBox.setWidth(TICKET_WIDTH);

		/*
		 *Setting the colour of the rectangle
		 *If it is in a blocked status it will be red
		 *		
		 */

		if (t.isBlockedStatus()){
			tickBox.setFill(Color.RED);
		}else{
			tickBox.setFill(Color.YELLOW);
		}



		//////////////////////////////////
		//Placing these in a stack pane //
		/////////////////////////////////
		StackPane layout = new StackPane();
		layout.getChildren().addAll(baseBox,tickBox, task);
		layout.setAlignment(task, Pos.TOP_LEFT);
		layout.setId("TickBox");
		//Creating the context menu
		ContextMenu ticketContext = new ContextMenu();
		MenuItem moveTick = new MenuItem("Move A Ticket");
		//Setting what happens when move a ticket  is clicked
		moveTick.setOnAction(e ->{
			moveATicket(t);
		});
		MenuItem moveTicketSub = new MenuItem("Move To SubLane");
		moveTicketSub.setOnAction(q ->{
			moveToSubLane(t);
		});
		MenuItem editTicket = new MenuItem("Edit A Ticket");
		editTicket.setOnAction(r -> {
			editTicket(t);
		});
		MenuItem removeSub = new MenuItem("Remove From local done lane");
		removeSub.setOnAction(pop ->{
			removeFromSub(t);

		});

		//Adding the items to the context menu
		ticketContext.getItems().addAll(moveTick,editTicket,moveTicketSub,removeSub);
		//@ TODO Stop context menu showing the remoce from sublane if ticket is not in sublane
		//Adding the event listener for the context menu to the layout
		layout.addEventHandler(MouseEvent.MOUSE_CLICKED,  (MouseEvent  me) ->  {
			if (me.getButton() == MouseButton.SECONDARY  || me.isControlDown())  {
				ticketContext.show(layout, me.getScreenX(), me.getScreenY());
			}  else  {
				ticketContext.hide();
			}
		});
		return layout;

	}

	/**
	 * Used to create titles for the top of lanes
	 * @param title The text for the top of the lanes
	 * @param sub If it is going to be used as the heading for a sub-lane
	 * @return A StackPane with the text centred in the middle and the 
	 */
	private StackPane createTitle(String title, boolean sub){
		StackPane pane = new StackPane();
		Text t = new Text(title);
		if (sub == false){
			t.setFont(Font.font("Aerial", FontWeight.BOLD, 12));
		}else{
			t.setFont(Font.font("Aerial", FontPosture.ITALIC, 12));
		}
		pane.getChildren().addAll(t);
		pane.setMinWidth(TICKET_WIDTH);
		pane.setId("titlePane");
		return pane;
	}

	private void moveATicket(Ticket t){
		boolean outcome = kc.moveATicket(t);
		System.out.println("The move operation for ticket "+t.getTicketName()+" was a: ");
		if (outcome == true){
			System.out.print("success");
			updateBoard();

		}else{
			Alert alert = new Alert(AlertType.ERROR, "Ticket is already in done lane");
			alert.show();
			System.out.print("failure");
		}
	}

	private void moveToSubLane(Ticket t){
		boolean outcome = kc.moveTicketToSub(t);
		if (outcome == false){
			window = new Stage();
			GridPane gp = createGridPane();
			window.setTitle("Error");
			Text windowTitle = new Text("Lane does not have a local done"
					+ "\nWould you like to create one");
			gp.add(windowTitle, 1, 1);
			Button yes =  new Button("Yes");
			gp.add(yes, 2, 3);
			Button no =  new Button("No");
			gp.add(no, 4, 3);
			Scene scene = new Scene(gp,300,130);
			window.setScene(scene);
			window.show();

			yes.setOnAction(e->{
				Lane lane = kc.laneHasTicket(t);
				lc = new LaneController(lane);
				boolean splitOutcome = lc.splitALane();
				if (splitOutcome == true){
					kc.replaceALane(lane);
					window.close();
					Alert alert = new Alert(AlertType.CONFIRMATION, "Success");
					alert.show();
					kc.moveTicketToSub(t);
					updateBoard();
				}
				else{
					window.close();
					Alert alert = new Alert(AlertType.ERROR, "Operation has failed, lane cannot be split");
					alert.show();
				}
			});
			no.setOnAction(r -> {
				window.close();
			});
		}
		updateBoard();
	}

	/**
	 * Updates the grid pane which displays all the ticket and lane data
	 * should be called after any change to the actual GUI of the board
	 */
	private void updateBoard(){
		ArrayList<Lane> listOfLanes = kanban.getLaneArray();
		mainGrid.getChildren().clear();
		int countColumn = 0;
		for(int count =0;  count<listOfLanes.size(); count++){

			//Getting the current lane
			Lane lane = listOfLanes.get(count);
			//Setting the title
			//adding it to the top of its column
			StackPane pane = createTitle(lane.getName(), false);
			mainGrid.add(pane,countColumn,0);
			//Getting the array of tickets from the lane
			ArrayList<Ticket> listOfTickets = lane.getLaneArr();
			//Adding in all the tickets
			for (int i =0; i<listOfTickets.size(); i++){				
				Ticket t = listOfTickets.get(i);
				mainGrid.add(createTicket(t), countColumn, 1+i);
			}
			//Part where we add sub lanes
			//Checking to see if it contains a sub lane
			if (lane.getBooleanArr()[3] == true){
				//Incrementing the column count
				countColumn++;
				//Creating a title
				StackPane subPane = createTitle("sub - "+lane.getName(), true);
				//Placing it a row below
				mainGrid.add(subPane, countColumn, 0);
				ArrayList<Ticket> subList = lane.getSubLaneArr();
				//Inserting in all tickets
				for (int y=0; y<subList.size(); y++){
					Ticket ticket = subList.get(y);
					mainGrid.add(createTicket(ticket), countColumn, 1+y);

				}
			}
			countColumn++;

		}

	}

	/**
	 * The right click menu for the ticket editing interface 
	 * @param ticket
	 */
	private void editTicket(Ticket ticket){
		//	kc = new KanbanController(kanban);
		window = new Stage();
		GridPane gp = new GridPane();
		window.setTitle("Edit Ticket");
		Text windowTitle = new Text ("Edit a Ticket");
		gp.add(windowTitle, 0, 0);

		Label task = new Label("Task Name:");
		gp.add(task, 0, 1);
		TextField taskInput = new TextField(ticket.getTicketName());
		gp.add(taskInput, 1, 1);

		Label blocked = new Label("Blocked Status");
		gp.add(blocked, 0, 2);
		CheckBox blockedStatus = new CheckBox();
		blockedStatus.setSelected(ticket.isBlockedStatus());
		gp.add(blockedStatus, 1, 2);

		Label priority = new Label("Set Priority");
		gp.add(priority, 0, 3);
		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(100);
		slider.setBlockIncrement(10);
		slider.setValue(ticket.getPriority());
		gp.add(slider,1,3);
		//////////////////////////////////
		//Event handlers for done button//
		//////////////////////////////////
		Button done = new Button("Done");
		done.setOnAction(e-> {
			boolean success = true;
			String taskTitle = taskInput.getText();
			if (taskTitle.length()>0){
				ticket.setTicketName(taskTitle);
				System.out.println("Name :"+taskInput.getText());
			}else{
				Alert alert= new Alert(Alert.AlertType.ERROR, "Please enter text in the task field");
				alert.show();
				success = false;
			}
			ticket.setBlockedStatus(blockedStatus.isSelected());
			System.out.println("Status: " + blockedStatus.isSelected());
			ticket.setPriority((int) slider.getValue());
			System.out.println("Priority: "+slider.getValue());
			if (success == true){
				kc.findATicketAndReplace(ticket);
				updateBoard();
				window.close();
			}
		});
		gp.add(done, 1, 4);
		Button delete = new Button("Delete Ticket");
		gp.add(delete, 2, 4);
		delete.setOnAction(e->{
			Lane lane = kc.laneHasTicket(ticket);
			lc.removeATicket(ticket, kanban.getCurrentUser());
			kc.replaceALane(lane);
			updateBoard();
			window.close();
		});
		if(kanban.getCurrentUser().isOwner() == false){
			slider.setDisable(true);
			delete.setDisable(true);
		}
		Scene scene = new Scene(gp,400,400);
		window.setScene(scene);
		window.show();
	}

	/**
	 * GUI for lane creation
	 */
	private void createNewLane(){
		window = new Stage();
		window.setTitle("Create New Lane");

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
			position++;
			if (name.length()>0){
				boolean validate = kc.createAUserLane(name, position);
				if (validate == false){
					Alert alert = new Alert(AlertType.ERROR, "You have entered an inavalid name or position");
					alert.show();
				}
				else{
					window.close();
					updateBoard();
				}
			}else{
				Alert alert = new Alert(AlertType.ERROR, "You have left the lane name section empty");
				alert.show();

			}
		});
	}

	/**
	 * GUI for lane deletion
	 */
	private void deleteALane(){
		window = new Stage();
		window.setTitle("Delete");

		Label laneName = new Label("Lane Name: ");
		TextField nameInput = new TextField();

		Button create = new Button("Delete");

		GridPane gp = createGridPane();
		gp.add(laneName, 0, 2);
		gp.add(nameInput, 1, 2);
		gp.add(create, 0, 4);

		Scene s = new Scene(gp,400,200);
		window.setScene(s);
		window.show();

		create.setOnAction(e2->{
			boolean validate = kc.removeALane(nameInput.getText());

			if (validate == false){
				Alert alert = new Alert(AlertType.ERROR, 
						"You have either entered an invalid name "
								+ "\nor the lane cannot be deleted"
								+ "\nor it contains tickets");
				alert.show();
			}
			else{
				window.close();
				updateBoard();
			}
		});
	}
	/**
	 * GUI for sub lane Creation
	 */
	private void createSubLane(){
		window = new Stage();
		window.setTitle("Create Sub-Lane");

		Label laneName = new Label("Lane Name: ");
		TextField nameInput = new TextField();

		Button create = new Button("Create Sub");

		GridPane gp = createGridPane();
		gp.add(laneName, 0, 2);
		gp.add(nameInput, 1, 2);
		gp.add(create, 0, 4);

		Scene s = new Scene(gp,400,200);
		window.setScene(s);
		window.show();

		create.setOnAction(e2->{
			String laneNameS = nameInput.getText();
			if (laneNameS.length()>0){
				Lane lane = kc.findALane(nameInput.getText());
				if (lane!=null){
					lc = new LaneController(lane);
					boolean outcome = lc.splitALane();
					if (outcome == true){
						kc.replaceALane(lane);
						System.out.println("Opeartion of splitting lane "+lane.getName()+ "was a success");
						window.close();
						updateBoard();
					}
					else {
						Alert alert = new Alert(AlertType.ERROR, "You have entered an inavalid name or position");
						alert.show();
					}
				}
			}else{
				Alert alert = new Alert(AlertType.ERROR, "You have entered an inavalid name or position");
				alert.show();
			}
		});

	}

	private void removeFromSub(Ticket ticket){
		boolean outcome = kc.removeFromSub(ticket);
		if (outcome == false){
			Alert alert = new Alert(AlertType.ERROR, "Not in a local done lane");
			alert.show();
		}
		updateBoard();
	}
	/**
	 * GUI for local sub deletion
	 */
	private void deleteSubLane(){
		window = new Stage();
		window.setTitle("Delete Local Done Lane");

		Label laneName = new Label("Lane Name: ");
		TextField nameInput = new TextField();

		Button delete = new Button("Delete Local-Done");

		GridPane gp = createGridPane();
		gp.add(laneName, 0, 2);
		gp.add(nameInput, 1, 2);
		gp.add(delete, 0, 4);

		Scene s = new Scene(gp,400,200);
		window.setScene(s);
		window.show();
		delete.setOnAction(e ->{
			String nameOfLane = nameInput.getText();
			if (nameOfLane.length()>0){
				Lane lane = kc.getALane(nameOfLane);
				if (lane != null){
					lc = new LaneController(lane);
					boolean splitWorked = lc.unSplitLane();
					if (splitWorked == true){
						kc.replaceALane(lane);
						window.close();
						updateBoard();
					}
					else{
						//Lane has Tickets or not split
						Alert alert = new Alert(AlertType.ERROR, "Lane has tickets or does not have a local done"  );
						alert.show();
					}
				}else{
					//Lane does not Exist
					Alert alert = new Alert(AlertType.ERROR, "Lane by name "+nameInput.getText()+" does not exist");
					alert.show();
				}
			}else{
				Alert alert = new Alert(AlertType.ERROR, "Please enter text in the field lane name");
				alert.show();
			}
		});
	}
	/**
	 * GUI for the left create ticket window
	 * @return
	 */
	private GridPane CreateTicketWindow(){
		GridPane gp =createGridPane();
		Text windowTitle = new Text ("Edit a Ticket");
		gp.add(windowTitle, 0, 0);

		Label task = new Label("Task Name:");
		gp.add(task, 0, 1);
		TextField taskInput = new TextField();
		gp.add(taskInput, 1, 1);

		Label blocked = new Label("Blocked Status");
		gp.add(blocked, 0, 2);
		CheckBox blockedStatus = new CheckBox();

		gp.add(blockedStatus, 1, 2);

		Label priority = new Label("Set Priority");
		gp.add(priority, 0, 3);
		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(100);
		slider.setBlockIncrement(10);
		slider.setShowTickMarks(true);
		gp.add(slider,1,3);

		Button done = new Button("Create");
		done.setOnAction(e-> {
			String task1 = taskInput.getText();
			if (task1.length()>0){
				kc = new KanbanController(kanban);
				Lane lane = kc.getALane("Backlog");
				Ticket ticket1 = new Ticket(kc.incrementID(),task1);
				ticket1.setTicketID(kc.incrementID());
				lc = new LaneController(lane);
				lc.addATicket(ticket1);
				updateBoard();
				taskInput.clear();
				blockedStatus.setSelected(false);
				slider.setValue(0);
			}else{
				Alert alert = new Alert(AlertType.ERROR,"Please enter text for field \"Task Name\"");
				alert.show();
			}
		});
		gp.add(done, 1, 4);

		if(kanban.getCurrentUser().isOwner() == false){
			slider.setDisable(true);
		}
		return gp;
	}
}