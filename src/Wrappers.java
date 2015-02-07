import java.io.Serializable;
import java.util.Arrays;

public class Wrappers {}



//All need to be Serializable

@SuppressWarnings("serial")
class JoinRequest extends Wrappers implements Serializable{
	
	private PlayerData playerData;
	
	public JoinRequest(PlayerData playerData) {this.setPlayerData(playerData);}
	public PlayerData getPlayerData() {return playerData;}
	public void setPlayerData(PlayerData playerData) {this.playerData = playerData;}
	
	public String toString(){
		return "{JoinRequest: " + playerData + " packet#: " + playerData.packetNumber + "}";
	}
}

@SuppressWarnings("serial")
class Answer extends Wrappers implements Serializable{
	
	private Responses response;
	private Reasons reasons[];
	
	public Answer(Responses response){
		this.setResponse(response);
	}
	
	public Answer(Responses response, Reasons ... reasons){
		this.setResponse(response);
		this.setReasons(reasons);
	}

	public Responses getResponse() {return response;}
	public void setResponse(Responses response) {this.response = response;}
	public Reasons[] getReasons() {return reasons;}
	public void setReasons(Reasons reasons[]) {this.reasons = reasons;}
	
	public String toString(){
		return "{Answer: " + response + (reasons == null ? "" : " " + Arrays.toString(reasons))  + "}";
	}
}

@SuppressWarnings("serial")
class HeartBeat extends Wrappers implements Serializable{
	
	//Need static since new Object sent each time
	//But do i need a counter for heart beat?
	public static int packetCounter;
	public int packetNumber;
	
	public HeartBeat(){
		packetNumber = packetCounter++;
	}
	
	public String toString(){
		return "{Heartbeat packet#:" + packetNumber + "}";
	}
	
}

//class TestWrapper extends Wrappers implements Serializable{
//	
//}
