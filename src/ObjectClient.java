import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ObjectClient {
	
	DatagramSocket client = new DatagramSocket();

	public ObjectClient() throws Exception {
		
		int clientPort = 256;
		
		send(new Rectangle(50,50));
		send(new Rectangle(40,30));
		send(new Rectangle(69,69));
		send(new Rectangle(69,69,69,69));
		
		client.close();
	}
	
	public void send(Object obj) throws Exception{
		ByteArrayOutputStream bao = new ByteArrayOutputStream(5000);
		ObjectOutputStream oos = new ObjectOutputStream(bao);
		
		oos.flush();
		oos.writeObject(obj);
		oos.flush();
		byte[] buffer = bao.toByteArray();
		
		//System.out.println(Arrays.toString(buffer));
		
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 255);
		client.send(pack);
	}
	

	public static void main(String[] args) throws Exception {
		new ObjectClient();
	}

}