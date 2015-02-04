import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


abstract public class ObjectServer {

	private int serverPort = 25565;
	private final int MAX_BUFFER_SIZE = 2000;
	private DatagramSocket server = new DatagramSocket(serverPort);
	
	public ObjectServer() throws Exception {
		
		System.out.println("Server Running...");
		
		while (true){
			receivedPacket(receiveObject());
		}
	}
	
	public Object receiveObject() throws Exception{
		byte[] buffer = new byte[MAX_BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		server.receive(packet);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(bais));
		Object o = is.readObject();
		is.close();
		return o;
	}
	
	abstract void receivedPacket(Object o);
	
	public static void main(String[] args) throws Exception {
		new Runner();
	}

}

class Runner{
	
	public Runner() throws Exception{
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectServer server = new ObjectServer(){

						@Override
						void receivedPacket(Object o) {
							System.out.println("Server Received: " + o);
							
							if (o instanceof JoinRequest){
								JoinRequest temp = (JoinRequest) o;
								System.out.println(temp.getName() + " tried joining the server with color: " + temp.getColor());
							}
							
						}
						
					};
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
