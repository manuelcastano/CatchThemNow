package controller;

import javafx.scene.control.Button;
import model.Ball;

public class ThreadBall implements Runnable{
	
	private Button b;
	private Ball ball;
	
	
	public ThreadBall(Button b, Ball ball) {
		this.b = b;
		this.ball = ball;
	}
	
	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Button getB() {
		return b;
	}
	public void setB(Button b) {
		this.b = b;
	}
	
	@Override
	public void run() {
		while(!ball.isStopped()) {
			if(ball.getDirection().equals("DERECHA")) {
				b.setLayoutX(b.getLayoutX()+1);
				if(b.getLayoutX() == 600-ball.getDiameter()) {
					ball.setDirection("IZQUIERDA");
					ball.setBounces(ball.getBounces()+1);
				}
			}
			else if(ball.getDirection().equals("IZQUIERDA")){
				b.setLayoutX(b.getLayoutX()-1);
				if(b.getLayoutX() == 0) {
					ball.setDirection("DERECHA");
					ball.setBounces(ball.getBounces()+1);
				}
			}
			else if(ball.getDirection().equals("ARRIBA")){
				b.setLayoutY(b.getLayoutY()-1);
				if(b.getLayoutY() == 25) {
					ball.setDirection("ABAJO");
					ball.setBounces(ball.getBounces()+1);
				}
			}
			else if(ball.getDirection().equals("ABAJO")){
				b.setLayoutY(b.getLayoutY()+1);
				if(b.getLayoutY() == 400-ball.getDiameter()) {
					ball.setDirection("ARRIBA");
					ball.setBounces(ball.getBounces()+1);
				}
			}
			try {
				Thread.sleep(ball.getWaitTime());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}
}
