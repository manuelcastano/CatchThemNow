package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Ball;
import model.Game;

public class WindowGameController implements Initializable{
	
	private Game game;
	@FXML
	private AnchorPane ap;
	private int stopped;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MenuBar mb = new MenuBar();
		Menu file = new Menu("file");
		Menu see = new Menu("See");
		MenuItem lg = new MenuItem("Load Game");
		MenuItem sg = new MenuItem("Save Game");
		MenuItem e = new MenuItem("Exit");
		MenuItem bs = new MenuItem("Best Scores");
		lg.setOnAction(p -> {
			loadGame();
		});
		e.setOnAction(p -> {
			exit();
		});
		bs.setOnAction(s -> {
			Stage stage = new Stage();
			AnchorPane anchor = new AnchorPane();
			Label l = new Label(game.bestPlayers());
			anchor.getChildren().add(l);
			Scene scene = new Scene(anchor, 200, 200);
			stage.setScene(scene);
			stage.setTitle("Best scores of the level");
			stage.show();
		});
		file.getItems().addAll(lg, sg, e);
		see.getItems().addAll(bs);
		mb.getMenus().addAll(file, see);
		mb.setMinWidth(600);
		mb.setMinHeight(25);
		ap.getChildren().add(mb);
	}
	
	public void loadGame() {
		try {
			Stage s = (Stage)ap.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Window.fxml"));
			Scene scene = new Scene(root,600,400);
			s.setScene(scene);
			s.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		Stage s = (Stage)ap.getScene().getWindow();
		s.close();
	}
	
	public void setFileBalls(String file) {
		try {
			game = new Game(file);
			loadBalls();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadBalls() {
		stopped = 0;
		ArrayList<Ball> balls = game.getBalls();
		for(int i = 0; i < balls.size(); i++) {
			Button b = new Button();
			b.setLayoutX(balls.get(i).getPosX());
			b.setLayoutY(balls.get(i).getPosY());
			b.setShape(new Circle(balls.get(i).getDiameter()/2));
			b.setMinSize(balls.get(i).getDiameter(), balls.get(i).getDiameter()); 
			b.setMaxSize(balls.get(i).getDiameter(), balls.get(i).getDiameter());
			b.setId(balls.get(i).getWaitTime()+"");
			int l = i;
			b.setOnAction(e -> {
				balls.get(l).setStopped(true);
				stopped++;
				if(stopped == balls.size()) {
					finish();
				}
			});
			Runnable ball = new ThreadBall(b, balls.get(i));
			new Thread(ball).start();
			ap.getChildren().add(b);
		}
	}
	
	public void finish() {
		int bounces = 0;
		ArrayList<Ball> balls = game.getBalls();
		for(int i = 0; i < balls.size(); i++) {
			bounces += balls.get(i).getBounces();
		}
		if(game.canJoinToTheHall(bounces)) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getClassLoader().getResource("view/WindowHall.fxml"));
				loader.load();
				WindowHallController whm = loader.getController();
				whm.setGame(game);
				whm.setBounces(bounces);
				Parent p = loader.getRoot();
				Stage s = (Stage)ap.getScene().getWindow();
				s.setScene(new Scene(p));
				s.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Window.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root,600,400);
			Stage s = (Stage) ap.getScene().getWindow();
			s.setScene(scene);
			s.show();
		}
	}
}
