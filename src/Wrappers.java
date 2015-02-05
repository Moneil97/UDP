import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;

public class Wrappers {}

//All need to be Serializable

@SuppressWarnings("serial")
class JoinRequest implements Serializable{
	
	private PlayerData playerData;
	
	public JoinRequest(PlayerData playerData) {
		this.setPlayerData(playerData);
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}
}

@SuppressWarnings("serial")
class Answer implements Serializable{
	
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
		return "Answer: " + response + (reasons == null ? "" : " " + Arrays.toString(reasons));
	}
}



@SuppressWarnings("serial")
class HeartBeat implements Serializable{
	
}
