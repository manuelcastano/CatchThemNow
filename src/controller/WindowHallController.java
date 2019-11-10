package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;
import model.Player;

public class WindowHallController implements Initializable{
	
	@FXML
	private TextField tf;
	private Game game;
	private int bounces;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void setGame(Game g) {
		game = g;
	}
	
	public void setBounces(int bounces) {
		this.bounces = bounces;
	}
	
	public void submit() {
		Player p = new Player(tf.getText(), bounces);
		try {
			game.addPlayerToHall(p);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Stage s = (Stage) tf.getScene().getWindow();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Window.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root,600,400);
		s.setScene(scene);
		s.show();
	}
}
