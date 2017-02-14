package guiWorkStation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StyleGUI {
	
	private final int WIDTH = 350, HEIGHT = 350, FONT_SIZE = 36;
	private JLabel text;
	private JCheckBox bold, italic;
	private JPanel panel;
	private JFrame frame;
	private JButton randomButton;
	
	public StyleGUI() {
		text = new JLabel("Dumb ass bitch");
		text.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN, FONT_SIZE));
		//text.setSize(20,20);
		text.setPreferredSize(new Dimension(340,100));
		text.setOpaque(false);
		text.setBackground(Color.cyan);
		text.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
		
		bold = new JCheckBox("bold");
		bold.setBackground(Color.cyan);
		
		italic = new JCheckBox("italic");
		italic.setBackground(Color.cyan);
		
		randomButton = new JButton("hi");
		
		Listener listener = new Listener();
		
		bold.addItemListener(listener);
		italic.addItemListener(listener);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.add(text);
		panel.add(bold);
		panel.add(Box.createVerticalGlue());
		panel.add(italic);
		panel.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
		panel.setBackground(Color.black);
		
		frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0,200));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.getContentPane().add(randomButton, BorderLayout.PAGE_START);
	}
	
	private void center() {
		Toolkit kit = frame.getToolkit();
		Dimension size = kit.getScreenSize();
		frame.setLocation(size.width/2 - frame.getWidth()/2, size.height/2 - frame.getWidth()/2);
	}
	
	public void display() {
		frame.pack();
		center();
		frame.setVisible(true);
	}
	
	private class Listener implements ItemListener {

		public void itemStateChanged(ItemEvent arg0) {
			int style = Font.PLAIN;
			
			if (bold.isSelected())
				style = Font.BOLD;
			
			if (italic.isSelected())
				style += Font.ITALIC;	
			text.setFont(new Font(Font.DIALOG_INPUT, style, FONT_SIZE));
			
		}
		
	}
	
}
