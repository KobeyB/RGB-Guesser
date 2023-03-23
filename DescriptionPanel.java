
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class DescriptionPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8678334555569391875L;
	private Color bgColor;
	private JButton playButton, exitButton;
	private ImageIcon exitIcon;
	private DescriptionWindow descriptionWindow;
	private Timer timer;
	private double seconds;
	private int colorIncrement;
	private int titleSize = 20;
	private int opacity = 0;
	private int titleY;
	private int count;
	private double x;
	
	public DescriptionPanel(DescriptionWindow descriptionWindow) {
		
		timer = new Timer(100, this);
		timer.start();
		
		titleY = (int) (this.getHeight()/2.0);
		
		this.descriptionWindow = descriptionWindow;
		
		bgColor = new Color(130, 150, 205);
		
		playButton = new JButton("PLAY");
		playButton.addActionListener(this);
		playButton.setPreferredSize(new Dimension(150, 50));
		playButton.setFont(new Font("Avenir", Font.PLAIN, 25));
		playButton.setVisible(false);
		
		exitIcon = new ImageIcon("/Users/kobeybuhr/Desktop/exit_icon2.png");
		
		exitButton = new JButton(exitIcon);
		exitButton.addActionListener(this);
		
		this.setBackground(bgColor);
		this.setLayout(new BorderLayout());
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(playButton);
		buttonsPanel.setBackground(bgColor);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(bgColor);
		
		Graphics2D g2d = (Graphics2D) g;

		drawTitle(g2d);
		
		g2d.setFont(new Font("Avenir", Font.PLAIN, 18));
		//g2d.setColor(Color.black);
		g2d.setColor(new Color(0, 0, 0, opacity));
		
		centerString(g2d, 100, "Guess the color of the background!");
		
		centerString(g2d, 130, "To play, enter the red, green, and blue values");
		
		centerString(g2d, 160, "you think match the color of the background.");
		
		centerString(g2d, 190, "Note that the numbers should be between 0 and 255.");
		
		centerString(g2d, 255, "(Hint: 0 0 0 is black and 255 255 255 is white)");
		
	}

	private void centerString(Graphics2D g2d, int y, String line) {
		int lineWidth = g2d.getFontMetrics().stringWidth(line);
		g2d.drawString(line, (int) (this.getWidth()/2.0) - (int) (lineWidth/2.0), y);
	}

	private void drawTitle(Graphics2D g2d) {
		
		g2d.setFont(new Font("Luminari", Font.PLAIN, titleSize));
		
		String title = "Color guesser";
		int titleWidth = g2d.getFontMetrics().stringWidth(title);
		int xValue = (int) (this.getWidth()/2.0) - (int)(titleWidth/2.0);
		if (count < 1) {
			titleY = (int) (this.getHeight()/2.0) - (int) (g2d.getFontMetrics().getHeight()/2.0);
			count++;
		}
		
		for (int i=0; i<title.length(); i++) {
			String c = title.charAt(i) + "";
			Color letterColor = null;
			switch(colorIncrement) {
			case 0: letterColor = Color.red;
			break;
			case 1: letterColor = Color.orange;
			break;
			case 2: letterColor = Color.yellow;
			break;
			case 3: letterColor = Color.green;
			break;
			case 4: letterColor = Color.cyan;
			break;
			case 5: letterColor = Color.blue;
			break;
			case 6: letterColor = Color.magenta;
			break;
			}
			g2d.setColor(letterColor);
			
			g2d.drawString(c, xValue, titleY); // yValue should end as 48
			xValue+=g2d.getFontMetrics().stringWidth(c);
			
			colorIncrement++;
			colorIncrement%=7;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			timer.stop();
			descriptionWindow.dispose();
			new GameWindow();
			
		}
		else if (e.getSource() == exitButton) {
			
		}
		else if (e.getSource() == timer) {
			seconds += 0.1;
			
			
			// pulse animation
			// multiplying 10 to have max value of 40 (including 30 added on)
			// adding 30 causes minimum value to be 20 instead of 0 
			
			if (seconds > 3.8) {
				x += 0.5;
				titleSize = (int) (6 * Math.cos(x/2.0)) + 34;
			}
			else titleSize = 40;
			
			
			// translate up animation
			
			if (seconds > 1.5 && titleY > 60) {
				titleY-=5;
			}
			
			// fade in animation
			
			if (opacity < 255 && seconds > 3.5) {
				opacity+=51;
				playButton.setVisible(true);
			}
			
			repaint();
		}
		
	}
	
}
