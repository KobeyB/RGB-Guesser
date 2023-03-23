
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;


public class ResultWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2187932936105731185L;

	public ResultWindow(int score, int rGuess, int gGuess, int bGuess, int r, int g, int b,
			GamePanel gamePanel, GameWindow window, JButton submitButton) {
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setUndecorated(true); // removes menubar at top of JFrame
		this.setLayout(new BorderLayout());		this.setSize(500, 375); // 4:3 ratio
		this.setLocationRelativeTo(gamePanel);
		this.setBackground(new Color(100, 100, 150));
		
		this.add(new ResultPanel(score, rGuess, gGuess, bGuess, r, g, b, this, gamePanel, window, submitButton), BorderLayout.CENTER);
		
		
		this.setVisible(true);
	}
	
}
