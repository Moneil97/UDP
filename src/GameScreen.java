import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameScreen extends JPanel {

	private ClientRunner game;

	public GameScreen(ClientRunner game) {
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics g1){
		super.paintComponents(g1);
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.blue);
		g.fill(new Rectangle(50,50,50,50));
	}

	private void say(Object s) {
		System.out.println(s);
	}
}
