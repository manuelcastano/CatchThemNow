package model;


public class Ball {
	
	private int diameter;
	private int posX;
	private int posY;
	private int waitTime;
	private String direction;
	private int bounces;
	private boolean stopped;
	
	public Ball(int diameter, int posX, int posY, int waitTime, String direction, int bounces, boolean stopped) {
		this.diameter = diameter;
		this.posX = posX;
		this.posY = posY;
		this.waitTime = waitTime;
		this.direction = direction;
		this.bounces = bounces;
		this.stopped = stopped;
	}
	
	public int getDiameter() {
		return diameter;
	}
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getBounces() {
		return bounces;
	}
	public void setBounces(int bounces) {
		this.bounces = bounces;
	}
	public boolean isStopped() {
		return stopped;
	}
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	
	public void moveBall() {
		if(direction.equals("DERECHA")) {
			posX++;
			if(posX == 600-(diameter/2)) {
				direction = "IZQUIERDA";
				bounces++;
			}
		}
		else if(direction.equals("IZQUIERDA")){
			posX--;
			if(posX == (diameter/2)) {
				direction = "DERECHA";
				bounces++;
			}
		}
		else if(direction.equals("ARRIBA")){
			posY--;
			if(posY == 25+(diameter/2)) {
				direction = "ABAJO";
				bounces++;
			}
		}
		else if(direction.equals("ABAJO")){
			posY++;
			if(posY == 400-(diameter/2)) {
				direction = "ARRIBA";
				bounces++;
			}
		}
	}

	@Override
	public String toString() {
		return diameter + "\t" + posX + "\t" + posY + "\t" + waitTime
				+ "\t" + direction + "\t" + bounces + "\t" + stopped;
	}
	
	
}
