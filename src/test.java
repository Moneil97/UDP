import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class test {

	public test() throws IOException {
		
		int port = 255;
		
		DatagramSocket server = new DatagramSocket(port);
		DatagramSocket client = new DatagramSocket();
		
		
		byte[] buf = {'h','i'};
		System.out.println(1);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				byte[] buf2 = new byte[1];
				DatagramPacket pak = new DatagramPacket(buf2, buf2.length);
				try {
					server.receive(pak);
					System.out.println(5);
					System.out.println(buf2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
		client.send(new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), port));
		System.out.println(2);
		
//		byte[] buf2 = new byte[1];
//		System.out.println(3);
//		DatagramPacket pak = new DatagramPacket(buf2, buf2.length);
//		System.out.println(4);
//		client.receive(pak);
//		System.out.println(5);
//		System.out.println(buf2);
		
//		server.close();
//		client.close();
		
		
		
		//server.
		
		
		
	}

	public static void main(String[] args) {
		try {
			new test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
