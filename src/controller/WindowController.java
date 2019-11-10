package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;

public class WindowController implements Initializable{
	
	@FXML
	private Label warning;
	@FXML
	private TextField file;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void enterFile() {
		if(!new File(file.getText()+".txt").exists()) {
			warning.setVisible(true);
		}
		else {
			warning.setVisible(false);
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getClassLoader().getResource("view/WindowGame.fxml"));
				loader.load();
				WindowGameController wgm = loader.getController();
				wgm.setFileBalls(file.getText()+".txt");;
				Parent p = loader.getRoot();
				Stage s = (Stage)file.getScene().getWindow();
				s.setScene(new Scene(p));
				s.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
