import java.awt.Color;
import java.io.Serializable;

public class Wrappers {}

//All need to be Serializable

@SuppressWarnings("serial")
class JoinRequest implements Serializable{
	
	private String name;
	private Color color;
	
	public JoinRequest(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() { return name; }
	public void setName(String name) {this.name = name;}
	public Color getColor() {return color;}
	public void setColor(Color color) {this.color = color;}
}

class Answer implements Serializable{
	
}

class HeartBeat implements Serializable{
	
}
