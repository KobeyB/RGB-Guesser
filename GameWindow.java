import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8367841369972906869L;
	GamePanel gamePanel;
	JPanel basePanel;

	public GameWindow() {
		gamePanel = new GamePanel(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 675); // 4:3 ratio
		this.setLocationRelativeTo(null);
		this.setTitle("RGB Guesser");
		this.setLayout(new BorderLayout());
		
		basePanel = new JPanel();
		
		this.add(basePanel);
		this.add(gamePanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void addNewGamePanel() {
		gamePanel = new GamePanel(this);
		this.add(gamePanel, BorderLayout.CENTER);
	}
	
}
