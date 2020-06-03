package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuGUI extends Application{	
	public MainMenuGUI() {
		
	}
	@Override
	public void start(Stage stage) throws Exception{
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUIOfProject.fxml"));
	        Parent root = (Parent) fxmlLoader.load();
	        Scene scene = new Scene(root,795,577);
	        stage.setTitle("PLOTS");
	        stage.setScene(scene);
	        stage.show();
	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
