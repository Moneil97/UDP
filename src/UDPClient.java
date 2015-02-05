import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class UDPClient {
	
	private DatagramSocket client;
	private final int MAX_BUFFER_SIZE = 2000;
	private InetAddress serverAddress;
	private int serverPort;

	public UDPClient(String ip, int port){// throws Exception {
		try {
			client = new DatagramSocket();
			this.serverPort = port;
			this.serverAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException | SocketException e1) {
			e1.printStackTrace();
		}
		
		//Receive Packets
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						receivedPacket(getPacket());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public Object getPacket() throws IOException, ClassNotFoundException{
		byte[] buffer = new byte[MAX_BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		client.receive(packet);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(bais));
		Object o = is.readObject();
		is.close();
		return o;
	}
	
	
	public void sendObject(Object obj) throws IOException{
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
}

