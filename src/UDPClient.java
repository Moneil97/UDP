import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class ObjectClient {
	
	private DatagramSocket client = new DatagramSocket();
	private InetAddress serverAddress;
	private int serverPort;

	public ObjectClient(String ip, int port) throws Exception {
		this.serverPort = port;
		this.serverAddress = InetAddress.getByName(ip);
	}
	
	public void sendObject(Object obj) throws Exception{
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bao);
		
		oos.flush();
		oos.writeObject(obj);
		oos.flush();
		byte[] buffer = bao.toByteArray();

		DatagramPacket pack = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
		client.send(pack);
	}
	
	abstract void receivedPacket(Object o);
	
	public static void main(String[] args) throws Exception {
		new Tester();
	}

}

class Tester{
	
	private boolean waitForAnswer = true;

	public Tester() throws Exception{
		String name = "Bob";
		Color color = Color.blue;
		//etc;
		
		ObjectClient client = new ObjectClient("localhost", 25565){

			@Override
			void receivedPacket(Object o) {
				
				if (o instanceof Answer){
					
//					if (yes)
//						waitForAnswer = false;
//					else
//						client.sendObject(new JoinRequest(newName, newColor));
				}
			}
		};
		
		client.sendObject(new JoinRequest(name, color));
		
		while (waitForAnswer ){
			
		}
		
	}
}

