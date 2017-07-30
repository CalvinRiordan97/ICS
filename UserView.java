package View;

import java.io.File;

import Controller.KanbanController;
import Controller.UserController;
import Model.Kanban;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


public class UserView {
	public Scene loginExisting(Kanban x){
		return null;
	}

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
	
	public Scene newUser(){
		return null;
	}
	
	public Scene loginDifferent(){
		return null;
	}
	private GridPane createGridPane(){
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(10);
		gp.setHgap(10);
		return gp;
	}
}
