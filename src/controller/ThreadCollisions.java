package controller;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import model.Ball;

public class ThreadCollisions implements Runnable{
	
	private ArrayList<Ball> balls;
	private ArrayList<Button> buttons;

	public ThreadCollisions(ArrayList<Ball> balls, ArrayList<Button> buttons) {
		this.balls = balls;
		this.buttons = buttons;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}
	
	public void addButton(Button b) {
		buttons.add(b);
	}

	@Override
	public void run() {
		while(true) {
			for(int i = 0; i < balls.size(); i++) {
				for(int j = 0; j < balls.size(); j++) {
					double cx1 = buttons.get(i).getScaleX();
					double cx2 = buttons.get(j).getScaleX();
					double cy1 = buttons.get(i).getScaleY();
					double cy2 = buttons.get(j).getScaleY();
					double distance = Math.sqrt( (cx1 - cx2)*(cx1 - cx2) + (cy1 - cy2)*(cy1 - cy2) );
					if ( distance < (balls.get(i).getDiameter()/2) + (balls.get(j).getDiameter()/2) ) {
						if(balls.get(i).getDirection().equals("IZQUIERDA")) {
							balls.get(i).setDirection("DERECHA");
						}
						else if(balls.get(i).getDirection().equals("DERECHA")) {
							balls.get(i).setDirection("IZQUIERDA");
						}
						else if(balls.get(i).getDirection().equals("ARRIBA")) {
							balls.get(i).setDirection("ABAJO");
						}
						else if(balls.get(i).getDirection().equals("ABAJO")) {
							balls.get(i).setDirection("ARRIBA");
						}
						if(balls.get(j).getDirection().equals("IZQUIERDA")) {
							balls.get(j).setDirection("DERECHA");
						}
						else if(balls.get(j).getDirection().equals("DERECHA")) {
							balls.get(j).setDirection("IZQUIERDA");
						}
						else if(balls.get(j).getDirection().equals("ARRIBA")) {
							balls.get(j).setDirection("ABAJO");
						}
						else if(balls.get(j).getDirection().equals("ABAJO")) {
							balls.get(j).setDirection("ARRIBA");
						}
					}
				}
			}
		}
	}

}
