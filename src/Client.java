import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class Client {

	public Client() throws Exception {
		int port = 256;
		DatagramSocket client = new DatagramSocket();
		
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				try {
					while (true){
						byte[] buffer = new byte[500];
						DatagramPacket packet = new DatagramPacket(buffer  , buffer.length);
						client.receive(packet);
						
						System.out.println("Client Buffer: " + Arrays.toString(buffer));
						
						//remove extra buffer
						for (int i =500-1; i>-1; i--){
							if (buffer[i] != 0){
								byte[] temp = new byte[i+1];
								for (int j =0; j <= i ; j++)
									temp[j] = buffer[j];
								buffer = temp;
								break;
							}
						}
						
						System.out.println("Client Buffer: " + Arrays.toString(buffer));
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		byte[] buffer = "Hello server".getBytes();
		client.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 255));
		
//		buffer = "Hello again server".getBytes();
//		client.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), port));
		
	}

	public static void main(String[] args) throws Exception {
		new server();
		new Client();
	}

}
