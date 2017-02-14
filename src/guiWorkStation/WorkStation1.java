package guiWorkStation;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class WorkStation1 extends JFrame {
	
	public WorkStation1() {
		super("WORK STATION1");
		setSize(500,200);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		center();
	}
	
	private void center() {
		Toolkit kit = super.getToolkit();
		Dimension size = kit.getScreenSize();
		this.setLocation(size.width/2 - this.getWidth()/2, size.height/2 - this.getHeight()/2);
	}
	
}
