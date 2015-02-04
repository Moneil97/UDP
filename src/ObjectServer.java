import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ObjectServer {

	public ObjectServer() throws Exception {
		
		System.out.println("Server Running...");
		
		int serverPort = 25565;
		DatagramSocket server = new DatagramSocket(serverPort);
		
		while (true){
			
			byte[] buffer = new byte[200];
			DatagramPacket packet = new DatagramPacket(buffer , buffer.length);
			server.receive(packet);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
			ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(bais));
			Object o = is.readObject();
			is.close();
			
			System.out.println(o);
			
		}
		
		
		//server.close();
	}

	public static void main(String[] args) throws Exception {
		new ObjectServer();
	}

}
