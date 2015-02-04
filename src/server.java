import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;


public class server {

	int port = 255;
	
	public server() throws Exception {
		
		System.out.println("Server running");
		
		DatagramSocket server = new DatagramSocket(port);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while (true){
						byte[] buffer = new byte[200];
						DatagramPacket packet = new DatagramPacket(buffer , buffer.length);
						server.receive(packet);
						
						System.out.println("Server Buffer: " + Arrays.toString(buffer));
											
						byte[] buf = "Your message was received".getBytes();
						//System.out.println("Message from: " + packet.getAddress());
						server.send(new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort()));
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		

	}

	public static void main(String[] args) throws Exception {
		new server();
	}

}
