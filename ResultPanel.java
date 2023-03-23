import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ResultPanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4542346675080078919L;
	private ResultWindow resultWindow;
	private GamePanel gamePanel;
	private GameWindow window;
	
	private int score;
	private int rGuess, gGuess, bGuess;
	private int rValue, gValue, bValue;
	private int centerX;
	private Font mainFont;
	private String evaluation;
	private Color guessedColor;
	private JButton playAgainButton, exitButton;
	private JButton submitButton;
	
	private Timer timer;
	private double seconds;
	
	public ResultPanel(int score, int rGuess, int gGuess, int bGuess, int r, int g, int b,
			ResultWindow resultWindow, GamePanel gamePanel, GameWindow window, JButton submitButton) {
		this.resultWindow = resultWindow;
		this.gamePanel = gamePanel;
		this.window = window;
		this.submitButton = submitButton;
		
		timer = new Timer(10, this);
		timer.start();
		
		this.score = score;
		this.rGuess = rGuess;
		this.gGuess = gGuess;
		this.bGuess = bGuess;
		rValue = r;
		gValue = g;
		bValue = b;
		guessedColor = new Color(rGuess, gGuess, bGuess);
		mainFont = new Font("Avenir", Font.BOLD, 30);
		evaluation = getEvaluation(score);
		
		this.setLayout(new BorderLayout());
		
		playAgainButton = new JButton("Play Again");
		playAgainButton.addActionListener(this);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setBackground(guessedColor);
		
		buttonsPanel.add(playAgainButton);
		buttonsPanel.add(exitButton);
				
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
	}
	
	private String getEvaluation(int score) {
		if (score < 600)
			return "Poor :(";
		if (score >= 600 && score < 750)
			return "Not bad :/";
		if (score >= 750 && score < 875)
			return "Good :)";
		if (score >= 875 && score < 950)
			return "Great :D";
		return "Amazing!! XD";
	}
	
	private boolean isDark() {
		if (rGuess*0.299 + gGuess*0.587 + bGuess*0.114 <= 110)
			return true;
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(guessedColor);
		
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
                
        g2d.setRenderingHints(rh);
        
        centerX = (int) (this.getWidth()/2.0);
		
		g2d.setFont(mainFont);
		if (isDark())
			this.setForeground(Color.lightGray);
		
		drawResultInfo(g2d);
		
		drawRings(g2d);
		
	}
	
	private void drawResultInfo(Graphics2D g2d) {
		
		int evaluationWidth = g2d.getFontMetrics().stringWidth(evaluation);
		g2d.drawString(evaluation, centerX - (int)(evaluationWidth/2.0), 40);
		
		String scoreString = "Score: " + score + "/1000";
		int scoreWidth = g2d.getFontMetrics().stringWidth(scoreString);
		g2d.drawString(scoreString, centerX - (int)(scoreWidth/2.0), 80);
		
		g2d.setFont(new Font("Avenir", Font.ITALIC, 16));
		
		int stringWidth = g2d.getFontMetrics().stringWidth("Your guess");
		g2d.drawString("Your guess", centerX - (int) (stringWidth/2.0), 110);
		
		stringWidth = g2d.getFontMetrics().stringWidth("Solution");
		g2d.drawString("Solution", centerX - (int) (stringWidth/2.0), 205);
		
		String note = "(The color you picked is the background of this box)";
		int noteWidth = g2d.getFontMetrics().stringWidth(note);
		g2d.drawString(note, centerX - (int)(noteWidth/2.0), 310);
		
	}

	private void drawRings(Graphics2D g2d) {
		int ringWidth = 50;
		int xValue;
		int yValue;
		
		g2d.setStroke(new BasicStroke(7));
		
		// Note that 0 degrees means that it starts at the 3 o'clock position (like a unit circle)
		
		// guessed rings
		
		xValue = (int) ((this.getWidth()-200) * 1/3.0)+125 - 100;
		yValue = 125;
		drawRing(g2d, xValue, yValue, ringWidth, Color.red, rGuess);
		
		xValue = (int) ((this.getWidth()-200) * 2/3.0)+125 - 100;
		drawRing(g2d, xValue, yValue, ringWidth, Color.green, gGuess);
		
		xValue = (int) (this.getWidth()-200)+125 - 100;
		drawRing(g2d, xValue, yValue, ringWidth, Color.blue, bGuess);
		
		
		// answer rings
		
		yValue = 220;
		
		xValue = (int) ((this.getWidth()-200) * 1/3.0)+125 - 100;
		drawRing(g2d, xValue, yValue, ringWidth, Color.red, rValue);
		
		xValue = (int) ((this.getWidth()-200) * 2/3.0)+125 - 100;
		drawRing(g2d, xValue, yValue, ringWidth, Color.green, gValue);
		
		xValue = (int) (this.getWidth()-200)+125 - 100;
		drawRing(g2d, xValue, yValue, ringWidth, Color.blue, bValue);
	
	}
	
	private void drawRing(Graphics2D g2d, int x, int y, int ringWidth, 
			Color color, int ringValue) {
		
		int amountOfRingFilled = (int) (ringValue * 360/255.0);
		
		g2d.setColor(color);
		
		g2d.drawArc(x, y, ringWidth, ringWidth, 90, (int) lerp(0, amountOfRingFilled, seconds));	
		
		if (isDark()) {
			g2d.setColor(Color.lightGray);
		}
		else {
			g2d.setColor(Color.black);
		}
		
		String stringValue = "" + ringValue;
		int stringWidth = g2d.getFontMetrics().stringWidth(stringValue);
		g2d.drawString("" + ringValue, (int) (x + (ringWidth/2.0 - stringWidth/2.0)),
				(int) (y + ringWidth/2.0) + 5);
	}
	
	public double lerp(double a, double b, double t) {
		if (t < 1)
			return a + t * (b - a);
		else
			return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playAgainButton) {
			resultWindow.dispose();
			
			// must use the following 4 lines to remove previous panel from window
			window.remove(gamePanel);
			window.addNewGamePanel();
			window.validate();
			window.repaint();
			
			submitButton.setEnabled(true);
		}
		else if (e.getSource() == exitButton) {
			timer.stop();
			resultWindow.dispose();
			window.dispose();
			System.exit(0); //line terminates java program 
			//(added since disposing window would not always terminate program)
		}
		else if (e.getSource() == timer) {
			seconds += 0.01f;
			repaint();
		}
	}
	
}
