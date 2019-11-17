package controller;

import javafx.scene.shape.Circle;
import model.Ball;

public class ThreadBall implements Runnable{
	
	private Ball ball;
	private Circle circle;
	
	public ThreadBall(Ball ball, Circle circle) {
		super();
		this.ball = ball;
		this.circle = circle;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	@Override
	public void run() {
		while(!ball.isStopped()) {
			ball.moveBall();
			circle.setCenterX(ball.getPosX());
			circle.setCenterY(ball.getPosY());
			try {
				Thread.sleep(ball.getWaitTime());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}
}
