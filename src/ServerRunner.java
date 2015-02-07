import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ServerRunner extends JFrame{

	private UDPServer server;
	private HashMap<Connection, Player> otherPlayers = new HashMap<Connection, Player>();
	
	public ServerRunner(){
		
		server = new UDPServer() {

			@Override
			void receivedPacket(Object object, InetAddress address, int port) {
				System.out.println("Server Received: " + object);

				if (object instanceof JoinRequest) {
					receivedJoinRequest(this, (JoinRequest) object, address, port);
				}
				else if (object instanceof Messages){
					
					Messages message = (Messages) object;
					
					if (message == Messages.CLIENT_CLOSING){
						
					}
				}
				else if (object instanceof PlayerData){
					receivedPlayerData(this, (PlayerData) object, address, port);
				}
			}
		};

	}
	
	private void receivedPlayerData(UDPServer server, PlayerData newdata, InetAddress address, int port) {

		Player player = otherPlayers.get(new Connection(address, port));
		
		if (newdata.packetNumber >= player.getLastPacketNumberReceived()){
			player.setLastPacketNumberReceived(newdata.packetNumber);
			//say(player.getLastPacketNumberReceived());
		}
		else{
			say("Throwing out old packet: " + newdata.packetNumber + " Current packet #: " + player.getLastPacketNumberReceived());
		}
		
	}

	private void receivedJoinRequest(UDPServer server, JoinRequest request, InetAddress address, int port){
		
		PlayerData playerdata = request.getPlayerData();
		List<Reasons> denyReasons = new ArrayList<Reasons>();
		
		for(Entry<Connection, Player> e :otherPlayers.entrySet()){
			if (e.getValue().getName().equals(playerdata.getName())){
				denyReasons.add(Reasons.NAME_IN_USE);
				break;
			}
		}
		for(Entry<Connection, Player> e :otherPlayers.entrySet()){
			if (e.getValue().getColor().equals(playerdata.getColor())){
				denyReasons.add(Reasons.COLOR_IN_USE);
				break;
			}
		}
		
		if (denyReasons.size() <= 0){
			server.sendObject(new Answer(Responses.ACCEPTED), address, port);
			otherPlayers.put(new Connection(address, port), new Player(playerdata));
		}
		else{
			server.sendObject(new Answer(Responses.DECLINED, denyReasons.toArray(new Reasons[denyReasons.size()])), address, port);
		}
	}
	
//	private void close(){
//		try{
//			server.close();
//		}catch(Exception e){}
//		System.exit(0);
//	}

	private void say(Object s) {
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		try {
			new ServerRunner();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

//Use Connection instead of only address so we can have multiple clients from the same ip address
class Connection {
	
	private InetAddress address;
	private int port;

	public Connection(InetAddress address, int port){
		this.setAddress(address);
		this.setPort(port);
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
	
	@Override
	public int hashCode(){
		return (address.hashCode() + port)/port; 
	}
	
	@Override
	public boolean equals(Object o){
		Connection other = (Connection) o;
		return (this.port == other.port && this.address.equals(other.address));
	}

}
