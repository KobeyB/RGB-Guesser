
import javax.swing.JFrame;

public class DescriptionWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -695012116477599583L;

	public DescriptionWindow() {
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(460, 345); // 4:3 ration
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		this.add(new DescriptionPanel(this));
		
		this.setVisible(true);
	}
	
}
