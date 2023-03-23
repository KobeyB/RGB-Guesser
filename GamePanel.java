import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7784263479714865009L;
	private JTextField textField;
	private String response;
	private JButton submitButton;
	private JPanel inputPanel;
	private Random rand;
	private Color bgColor;
	private int r, g, b;
	private int score;
	
	GameWindow window;
	
	public GamePanel(GameWindow window) {
		this.window = window;
		
		rand = new Random();
		r = rand.nextInt(255);
		g = rand.nextInt(255);
		b = rand.nextInt(255);
		
		bgColor = new Color(r, g, b);
		
		this.setBackground(bgColor);
		this.setLayout(new GridLayout(2, 1));
		
		textField = new JTextField("R G B");
		textField.setPreferredSize(new Dimension(210, 50));
		textField.setFont(new Font("Avenir", Font.PLAIN, 30));
		
		submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Avenir", Font.PLAIN, 18));
		submitButton.addActionListener(this);
		submitButton.setEnabled(true);
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(bgColor);
		
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputPanel.add(textField);
		inputPanel.add(submitButton);
		inputPanel.setBackground(bgColor);
		
		this.add(topPanel);
		this.add(inputPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			response = textField.getText();
			
			Scanner scan = new Scanner(response);
			
			try {
				int rGuess = scan.nextInt();
				int gGuess = scan.nextInt();
				int bGuess = scan.nextInt();
				scan.close();
				try {
					new Color(rGuess, gGuess, bGuess); // color not used yet (just checks for thrown exception)
					score = calculateScore(rGuess, gGuess, bGuess);
					new ResultWindow(score, rGuess, gGuess, bGuess, r, g, b, this, window, submitButton);
					submitButton.setEnabled(false);
					
				}
				catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(this,
							"Invalid entry. \nValues must be between 0 and 255 (inclusive).",
							null, JOptionPane.WARNING_MESSAGE);
				}
			}
			catch (NoSuchElementException ex) {
				JOptionPane.showMessageDialog(this,
						"Invalid entry. \nValues must be 3 numbers seperated by a space.",
						null, JOptionPane.WARNING_MESSAGE);
			}

		}
		
	}
	
	private int calculateScore(int rGuess, int gGuess, int bGuess) {
		return 1000 - (Math.abs(r-rGuess)) - (Math.abs(g-gGuess)) - (Math.abs(b-bGuess));
	}
	
}
