import java.awt.Color;
import java.io.Serializable;


public class Player {

	private PlayerData data;
	
	public Player(String name, Color color) {
		data = new PlayerData(name, color);
	}
	
	public PlayerData getPlayerData(){
		return data;
	}
	
	public String getName(){
		return data.getName();
	}
	
	public void setName(String name) {
		data.setName(name);
	}

	public Color getColor() {
		return data.getColor();
	}

	public void setColor(Color color) {
		data.setColor(color);
	}
}

@SuppressWarnings("serial")
class PlayerData implements Serializable{

	private String name;
	private Color color;
	
	public PlayerData(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
