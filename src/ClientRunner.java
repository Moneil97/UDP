import java.awt.Color;


public class ClientRunner {

	private boolean waitForAnswer = true;

	public ClientRunner() throws Exception{
		String name = "Bob";
		Color color = Color.blue;
		//etc;
		
		UDPClient client = new UDPClient("localhost", 25565){

			@Override
			void receivedPacket(Object object) {
				
				say("Client Received: " + object);
				
				if (object instanceof Answer){
					
					Answer temp = (Answer) object;

					if (temp.getResponse() == Responses.ACCEPTED){
						waitForAnswer = false;
					}
					else{
						
						if (temp.getReasons()[0] == Reasons.NAME_IN_USE){
							try {
								
								//make a Permant player object and modify it before sending
								
								sendObject(new JoinRequest(new Player("Jim", Color.red).getPlayerData()));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
				}
			}
		};
		
		
		//make a Permant player object and modify it before sending
		client.sendObject(new JoinRequest(new Player(name, color).getPlayerData()));
		
		while (waitForAnswer ){
			Thread.sleep(200);
		}
		
		say("Joined Server");
		
	}

	private void say(Object s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		try {
			new ClientRunner();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
