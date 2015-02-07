import java.awt.Color;
import java.io.Serializable;


public class Player {

	private PlayerData data;
	private int lastPacketNumberReceived = 0; //For Server Use
	
	public Player(String name, Color color) {
		data = new PlayerData(name, color);
	}
	
	public Player(PlayerData playerdata) {
		data = playerdata;
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

	public Player incPacketNum() {
		data.incPacketNum();
		return this;
	}

	//For Server Use Only
	public void setLastPacketNumberReceived(int packetNumber) {
		lastPacketNumberReceived = packetNumber;
	}
	
	public int getLastPacketNumberReceived(){
		return lastPacketNumberReceived;
	}

//	public PlayerData sendPlayerData() {
//		data.incPacketNum();
//		return data;
//	}
}

@SuppressWarnings("serial")
class PlayerData implements Serializable{

	//public static int packetCounter;
	public int packetNumber;
	private String name;
	private Color color;
	
	public PlayerData(String name, Color color) {
		packetNumber = 0;//packetCounter++;
		this.name = name;
		this.color = color;
	}
	
	public void incPacketNum() {
		packetNumber++;
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
	
	public String toString(){
		return "{PlayerData: " + name + " " + color + " packet#: " + packetNumber + "}";
	}
}
