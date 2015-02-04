import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Light_Controller extends JFrame{

	private static final long serialVersionUID = 1L;

	Socket sock = null;//= new Socket("oneil.asuscomm.com", 1336);
	
	public Light_Controller() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnOn = new JButton("ON");
		btnOn.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sock == null)
					try {
						sock = new Socket("oneil.asuscomm.com", 1336);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
				try {
					sock.getOutputStream().write("/Son/E".getBytes());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		panel.add(btnOn);
		
		JButton btnOff = new JButton("OFF");
		btnOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sock == null)
					try {
						sock = new Socket("oneil.asuscomm.com", 1336);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
				
				try {
					sock.getOutputStream().write("/Soff/E".getBytes());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnOff);
		
		
		setSize(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new Light_Controller();
	}

}
