package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Game {
	
	public final static String PLAYERS0 = "Players0.txt";
	public final static String PLAYERS1 = "Players1.txt";
	public final static String PLAYERS2 = "Players2.txt";
	
	private ArrayList<Ball> balls;
	private ArrayList<Player> players0;
	private ArrayList<Player> players1;
	private ArrayList<Player> players2;
	private String fileBalls;
	private int level;
	
	public Game(String fileBalls) throws IOException, ClassNotFoundException {
		balls = new ArrayList<Ball>();
		players0 = new ArrayList<Player>();
		players1 = new ArrayList<Player>();
		players2 = new ArrayList<Player>();
		this.fileBalls = fileBalls;
		loadBalls();
		loadPlayers0();
		loadPlayers1();
		loadPlayers2();
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getFileBalls() {
		return fileBalls;
	}

	public void setFileBalls(String fileBalls) {
		this.fileBalls = fileBalls;
	}

	public ArrayList<Player> getPlayers2() {
		return players2;
	}

	public void setPlayers2(ArrayList<Player> players2) {
		this.players2 = players2;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}
	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}
	public ArrayList<Player> getPlayers0() {
		return players0;
	}
	public void setPlayers0(ArrayList<Player> players0) {
		this.players0 = players0;
	}
	public ArrayList<Player> getPlayers1() {
		return players1;
	}
	public void setPlayers1(ArrayList<Player> players1) {
		this.players1 = players1;
	}
	public void addBall(Ball b) {
		balls.add(b);
	}
	
	public void loadBalls() throws IOException {
		File f = new File(fileBalls);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		while((line = br.readLine()) != null) {
			if(!(line.charAt(0) == '#')) {
				if(line.length() == 1) {
					level = Integer.parseInt(line);
				}
				else {
					String[] sp = line.split("\t");
					Ball b = new Ball(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]), Integer.parseInt(sp[2]), Integer.parseInt(sp[3]), sp[4], Integer.parseInt(sp[5]), Boolean.parseBoolean(sp[6]));
					addBall(b);
				}
			}
		}
		br.close();
	}
	
	public void loadPlayers0() throws FileNotFoundException, IOException, ClassNotFoundException {
		File f = new File(PLAYERS0);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		players0 = (ArrayList<Player>) ois.readObject();
		ois.close();
	}
	
	public void loadPlayers1() throws FileNotFoundException, IOException, ClassNotFoundException {
		File f = new File(PLAYERS1);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		players1 = (ArrayList<Player>) ois.readObject();
		ois.close();
	}
	
	public void loadPlayers2() throws FileNotFoundException, IOException, ClassNotFoundException {
		File f = new File(PLAYERS2);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		players2 = (ArrayList<Player>) ois.readObject();
		ois.close();
	}
	
	public void writePlayers0() throws FileNotFoundException, IOException {
		File f = new File(PLAYERS0);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(players0);
		oos.close();
	}
	
	public void writePlayers1() throws FileNotFoundException, IOException {
		File f = new File(PLAYERS1);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(players1);
		oos.close();
	}
	
	public void writePlayers2() throws FileNotFoundException, IOException {
		File f = new File(PLAYERS2);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(players2);
		oos.close();
	}
	
	public void addPlayerToHall(Player p) throws FileNotFoundException, IOException {
		boolean added = false;
		if(level == 0) {
			if(players0.isEmpty()) {
				players0.add(p);
				added = true;
			}
			else {
				for(int i = 0; i < players0.size() && !added; i++) {
					if(players0.get(i).compareTo(p) > 0) {
						players0.add(i, p);
						if(players0.size() == 11) {
							players0.remove(10);
						}
						added = true;
					}
				}
				if(!added) {
					if(players0.size() < 10) {
						players0.add(p);
						added = true;
					}
				}
			}
			writePlayers0();
		}
		if(level == 1) {
			if(players1.isEmpty()) {
				players1.add(p);
				added = true;
			}
			else {
				for(int i = 0; i < players1.size() && !added; i++) {
					if(players1.get(i).compareTo(p) > 0) {
						players1.add(i, p);
						if(players1.size() == 11) {
							players1.remove(10);
						}
						added = true;
					}
				}
				if(!added) {
					if(players1.size() < 10) {
						players1.add(p);
						added = true;
					}
				}
			}
			writePlayers1();
		}
		if(level == 2) {
			if(players2.isEmpty()) {
				players2.add(p);
				added = true;
			}
			else {
				for(int i = 0; i < players2.size() && !added; i++) {
					if(players2.get(i).compareTo(p) > 0) {
						players2.add(i, p);
						if(players2.size() == 11) {
							players2.remove(10);
						}
						added = true;
					}
				}
				if(!added) {
					if(players1.size() < 10) {
						players1.add(p);
						added = true;
					}
				}
			}
			writePlayers2();
		}
	}
	
	public boolean canJoinToTheHall(int score) {
		boolean join = false;
		if(level == 0) {
			if(players0.isEmpty()) {
				join = true;
			}
			else {
				for(int i = 0; i < players0.size(); i++) {
					if(players0.get(i).getScore() > score) {
						join = true;
					}
				}
				if(!join) {
					if(players0.size() != 10) {
						join = true;
					}
				}
			}
		}
		if(level == 1) {
			if(players1.isEmpty()) {
				join = true;
			}
			else {
				for(int i = 0; i < players1.size(); i++) {
					if(players1.get(i).getScore() > score) {
						join = true;
					}
				}
				if(!join) {
					if(players1.size() != 10) {
						join = true;
					}
				}
			}
		}
		if(level == 2) {
			if(players2.isEmpty()) {
				join = true;
			}
			else {
				for(int i = 0; i < players2.size(); i++) {
					if(players2.get(i).getScore() > score) {
						join = true;
					}
				}
				if(!join) {
					if(players2.size() != 10) {
						join = true;
					}
				}
			}
		}
		return join;
	}
	
	public String bestPlayers() {
		String msg = "";
		if(level == 0) {
			for(int i = 0; i < players0.size(); i++) {
				msg += players0.get(i).toString()+"\n";
			}
		}
		else if(level == 1) {
			for(int i = 0; i < players1.size(); i++) {
				msg += players1.get(i).toString()+"\n";
			}
		}
		else {
			for(int i = 0; i < players2.size(); i++) {
				msg += players2.get(i).toString()+"\n";
			}
		}
		return msg;
	}
	
	public void saveGame() throws IOException {
		String msg = "#nivel"+"\n";
		msg += level+"\n";
		msg += "#diametro posX posY espera direccion rebotes"+"\n";
		for(int i = 0; i < balls.size(); i++) {
			msg += balls.get(i).toString()+"\n";
		}
		File f = new File(fileBalls);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write(msg);
		bw.close();
	}
}
