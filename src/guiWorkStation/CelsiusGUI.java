package guiWorkStation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CelsiusGUI {
	
	private final int HEIGHT = 85, WIDTH = 300;
	
	private JFrame frame;
	private JPanel panel;
	private JLabel inputLabel, outputLabel, resultLabel;
	private JTextField textField;
	private JButton button, button1;
	
	
	public CelsiusGUI() {
		
		frame = new JFrame("Celsius Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		inputLabel = new JLabel("Enter Celsius temperature:");
		outputLabel = new JLabel("Temperature in Fahrenheit: ");
		resultLabel = new JLabel("----");
		JLabel scapeGoat = new JLabel("                ");
		
		textField = new JTextField(5);
		textField.setBackground(Color.yellow);
		textField.addActionListener(new TempListener());
		button = new JButton("Convert");
		button.setBackground(Color.yellow);
		button.addActionListener(new TempListener());
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setBackground(Color.yellow);
		
		panel.add(inputLabel);
		panel.add(textField);
		panel.add(outputLabel);
		panel.add(resultLabel);
		panel.add(scapeGoat);
		panel.add(button);
		frame.getContentPane().add(panel);
	}
	
	private void center() {
		Toolkit kit = frame.getToolkit();
		Dimension size = kit.getScreenSize();
		frame.setLocation(size.width/2 - frame.getWidth()/2, size.height/2 - frame.getHeight()/2);
	}
	
	public void display() {
		frame.pack();
		center();
		frame.setVisible(true);
	}
	
	private class TempListener implements ActionListener {
		
		public void actionPerformed (ActionEvent event) {
			int fahrenheit, celsius;
			fahrenheit = Integer.parseInt(textField.getText());
			
			celsius = (int) (1.8*(double)fahrenheit) + 32;
			String celsiusString = String.valueOf(celsius);
			resultLabel.setText(celsiusString);
		}
	}

}
