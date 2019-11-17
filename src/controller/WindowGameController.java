package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
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
		MenuItem ag = new MenuItem("About the game");
		ag.setOnAction(x -> {
			aboutTheGame();
		});
		sg.setOnAction(y -> {
			saveGame();
		});
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
		see.getItems().addAll(bs, ag);
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
			if(balls.get(i).isStopped()) {
				stopped++;
			}
			Circle circle = new Circle(balls.get(i).getDiameter()/2);
			circle.setCenterX(balls.get(i).getPosX());
			circle.setCenterY(balls.get(i).getPosY());
			ap.getChildren().add(circle);
			Runnable ball = new ThreadBall(balls.get(i), circle);
			new Thread(ball).start();
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
	
	public void stopBall(MouseEvent e) {
		ArrayList<Ball> balls = game.getBalls();
		for(int i = 0; i < balls.size(); i++) {
			if(!balls.get(i).isStopped()) {
				double x = e.getSceneX()-(balls.get(i).getPosX());
				x *= x;
				double y = e.getSceneY()-(balls.get(i).getPosY());
				y *= y;
				double sum = x+y;
				double radius = balls.get(i).getDiameter()/2;
				radius *= radius;
				if(sum <= radius) {
					balls.get(i).setStopped(true);
					stopped++;
				}
			}
		}
		if(stopped == balls.size()) {
			finish();
		}
	}
	
	public void saveGame() {
		try {
			game.saveGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exit();
	}
	
	public void aboutTheGame() {
		Stage stage = new Stage();
		AnchorPane anchor = new AnchorPane();
		String msg = "En el juego aparecen unas esferas en la pantalla moviéndose, "
				+ "algunas horizontal y otras verticalmente. "
				+ "Durante su movimiento, "
				+ "si la esfera alcanza un extremo de la ventana de juego, "
				+ "ésta rebotará  y se moverá ahora en sentido contrario. "
				+ "El jugador debe detenerlas haciendo clic sobre cada una de las esferas que aparecen en la pantalla, "
				+ "lo más rápido posible y antes de que reboten. Por cada rebote, el contador de rebotes aumentará. "
				+ "El mejor jugador es aquel que detenga todas las esferas con la menor cantidad de rebotes.\r\n";
		Label l = new Label(msg);
		l.setMaxSize(300, 300);
		l.setWrapText(true);
		anchor.getChildren().add(l);
		Scene scene = new Scene(anchor, 300, 300);
		stage.setScene(scene);
		stage.setTitle("About the game");
		stage.show();
	}
}
