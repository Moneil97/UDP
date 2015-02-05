import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ClientRunner extends JFrame{

	private String[] names = "Bob Jim Sandra Guy James Joe".split(" ");
	private Color[] colors = {Color.blue, Color.red, Color.green, Color.orange, Color.cyan};
	private Player you = null;
	private boolean waitForAnswer = true;
	private int serverPort = 255;
	private UDPClient client;

	public ClientRunner() throws Exception {
		
		client = new UDPClient("localhost", serverPort){

			@Override
			void receivedPacket(Object object) {
				
				say("Client Received: " + object);
				
				if (object instanceof Answer){
					
					Answer temp = (Answer) object;

					if (temp.getResponse() == Responses.ACCEPTED){
						waitForAnswer = false;
					}
					else{
						if (arrayContains(temp.getReasons(), Reasons.NAME_IN_USE))
							you.setName(getRandomName());
						if (arrayContains(temp.getReasons(), Reasons.COLOR_IN_USE))
							you.setColor(getRandomColor());
						sendObject(new JoinRequest(you.getPlayerData()));
					}
				}
				else if (object instanceof Messages){
					
					Messages message = (Messages) object;
					
					if (message == Messages.SERVER_CLOSING){
						close();
					}
				}
			}
		};
		
		//Get user input here
		you = new Player(getRandomName(), getRandomColor());
		client.sendObject(new JoinRequest(you.getPlayerData()));
		
		while (waitForAnswer ){
			Thread.sleep(200);
		}
		say("Joined Server");
		
	}
	
	private boolean arrayContains(Object[] array, Object object){
		for (Object check : array){
			if (check.equals(object))
				return true;
		}
		return false;
	}
	
	private String getRandomName(){
		return names[getRandom(0, names.length)];
	}
	
	private Color getRandomColor(){
		return colors[getRandom(0, colors.length)];
	}
	
	private int getRandom(int lower, int upper){
		return new Random().nextInt(upper-lower) + lower;
	}
	
	public void close(){
		try{
			client.close();
		}catch(Exception e){}
		System.exit(0);
	}

	private void say(Object s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		try {
			new ClientRunner();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

