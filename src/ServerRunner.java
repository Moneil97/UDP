import java.net.InetAddress;

public class ServerRunner {

	public ServerRunner() throws Exception {
		
		UDPServer server = new UDPServer() {

			@Override
			void receivedPacket(Object object, InetAddress address, int port) {
				System.out.println("Server Received: " + object);

				if (object instanceof JoinRequest) {
					JoinRequest temp = (JoinRequest) object;
//					System.out.println(temp.getName()
//							+ " tried joining the server with color: "
//							+ temp.getColor());
					
					System.out.println(temp.getPlayerData().getName()
							+ " tried joining the server with color: "
							+ temp.getPlayerData().getColor());
					
					if (temp.getPlayerData().getName().equals("Bob")){
						try {
							sendObject(new Answer(Responses.DECLINED, Reasons.NAME_IN_USE), address, port);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else{
						try {
							sendObject(new Answer(Responses.ACCEPTED), address, port);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		};
	}

	public static void main(String[] args) {
		try {
			new ServerRunner();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
