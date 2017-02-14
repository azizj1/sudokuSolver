package guiWorkStation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CaraballoPietri_Omar_lab_07_01 extends JFrame
{
	final int wWidth = 500;
	final int wHeight = 300;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JLabel directions;

	public CaraballoPietri_Omar_lab_07_01()
	{
		setTitle("Color Factory");
		setSize(wWidth, wHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//getContentPane().setLayout(new BorderLayout());

		topPanel();
		bottomPanel();

		directions = new JLabel("Top buttons change the panel color and bottom radio buttons change the text color");

		add(topPanel, BorderLayout.NORTH);
		add(directions, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	private void topPanel()
	{

		//setLayout(new FlowLayout());

		JButton bRed = new JButton("Red");
		bRed.setBackground(Color.RED);
		bRed.addActionListener(new ButtonListener());
		bRed.setActionCommand("red");

		JButton bOrange = new JButton("Orange");
		bOrange.setBackground(Color.ORANGE);
		bOrange.setActionCommand("orange");
		bOrange.addActionListener(new ButtonListener());

		JButton bYellow = new JButton("Yellow");
		bYellow.setBackground(Color.YELLOW);
		bYellow.setActionCommand("yellow");
		bYellow.addActionListener(new ButtonListener());

		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		topPanel.add(bRed);
		topPanel.add(bOrange);
		topPanel.add(bYellow);
	}
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals("red"))
			{
				getContentPane().setBackground(Color.RED);
			}
			else if(actionCommand.equals("orange"))
			{
				getContentPane().setBackground(Color.ORANGE);
			}
			else if(actionCommand.equals("yellow"))
			{
				getContentPane().setBackground(Color.YELLOW);
			}
		}
	}
	private void bottomPanel()
	{
		RadioButtonListener test = new RadioButtonListener();
		JRadioButton rGreen = new JRadioButton("Green");
		rGreen.setBackground(Color.GREEN);
		rGreen.setActionCommand("Green");
		rGreen.addActionListener(test);

		JRadioButton rBlue = new JRadioButton("Blue");
		rBlue.setBackground(Color.BLUE);
		rBlue.setActionCommand("Blue");
		rBlue.addActionListener(test);

		JRadioButton rCyan = new JRadioButton("Cyan");
		rCyan.setBackground(Color.CYAN);
		rCyan.setActionCommand("Cyan");
		rCyan.addActionListener(test);

		ButtonGroup radio = new ButtonGroup();
		radio.add(rGreen);
		radio.add(rBlue);
		radio.add(rCyan);

		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		//bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(rGreen);
		bottomPanel.add(rBlue);
		bottomPanel.add(rCyan);
	}
	private class RadioButtonListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals("Green"))
			{
				directions.setForeground(Color.GREEN);
			}
			else if (actionCommand.equals("Blue"))
			{
				directions.setForeground(Color.BLUE);
			}
			else if (actionCommand.equals("Cyan"))
			{
				directions.setForeground(Color.CYAN);
			}
		}
	}
	public static void main (String[ ] args)
	{
		new CaraballoPietri_Omar_lab_07_01();
	}
}