import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

abstract public class UDPServer {

	private int serverPort = 255;
	private final int MAX_BUFFER_SIZE = 2000;
	private DatagramSocket server;
	
	public UDPServer(){
		
		try {
			server = new DatagramSocket(serverPort);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Server Running on port: " + server.getLocalPort());
	
		//Receive Packets
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						Pack pack = getPacket();
						receivedPacket(pack.getObject(), pack.getAddress(), pack.getPort());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public DatagramSocket getDatagramSocket(){
		return server;
	}
	
	public void sendObject(Object obj, InetAddress address, int port){
		try{
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bao);
			
			oos.flush();
			oos.writeObject(obj);
			say("Sent: " + obj + " to: " + address + "@" + port);
			oos.flush();
			byte[] buffer = bao.toByteArray();
	
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length, address, port);
			server.send(pack);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void say(Object s) {
		System.out.println(s);
	}

	private Pack getPacket() throws Exception{
		byte[] buffer = new byte[MAX_BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		server.receive(packet);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(bais));
		Object o = is.readObject();
		is.close();
		return new Pack(o, packet.getAddress(), packet.getPort());
	}
	
	abstract void receivedPacket(Object o, InetAddress address, int port);

	public void close() {
		server.close();
	}

}

class Pack{
	
	private Object object;
	private InetAddress address;
	private int port;
	
	public Pack(Object o, InetAddress address, int port){
		this.object = o;
		this.address = address;
		this.port = port;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
