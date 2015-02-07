import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ClientRunner extends JFrame{

	private String ip = "localhost";
	private int serverPort = 255;
	
	private String[] names = "Bob Jim Sandra Guy James Joe".split(" ");
	private Color[] colors = {Color.blue, Color.red, Color.green, Color.orange, Color.cyan};
	private Player you = null;
	private boolean waitForAnswer = true;
	private UDPClient client;

	public ClientRunner() throws Exception {
		
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		client = new UDPClient(ip, serverPort){

			@Override
			void receivedPacket(Object object){
				
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
		
		this.add(new GameScreen(this));
		
		revalidate();
		repaint();
		
		say("done");
		
		
		SimpleTimer sendHeartbeatTimer = new SimpleTimer(true);
		sendHeartbeatTimer.setInterval(5);
		SimpleTimer sendPlayerDataTimer = new SimpleTimer(true);
		sendPlayerDataTimer.setInterval(1/4.0);
		SimpleTimer paintTimer = new SimpleTimer(true);
		paintTimer.setInterval(1/30.0);
		
		while (true){
			
			if (sendHeartbeatTimer.isReady()){
				sendHeartbeatTimer.reset();
				client.sendObject(new HeartBeat());
			}
			
			if (sendPlayerDataTimer.isReady()){
				sendPlayerDataTimer.reset();
				client.sendObject(you.incPacketNum().getPlayerData());
			}
			if(paintTimer.isReady()){
				paintTimer.reset();
				repaint();
			}
			
			Thread.sleep(10);
			
		}
		
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

