package guiWorkStation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import sudoku.SudokuBoard;
import sudoku.SudokuDecipher;


public class SudokuFrame extends JFrame {
	
	SudokuGrid grid;
	JPanel buttons;
	JButton solve,clear;
	private final static String TITLE 	= "Sudoku Decipher                   Copyright © 2010 Aziz Javed";
	
	public SudokuFrame() {
		super(TITLE);
		this.setIconImage(getIcon());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		grid = new SudokuGrid();
		computeButtons();
		
		this.getContentPane().add(grid,BorderLayout.CENTER);
		this.getContentPane().add(buttons,BorderLayout.PAGE_END);
		
	}
	
	private void computeButtons() {
		Border buttonBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Dimension buttonSize = new Dimension(90,30);
		Font buttonFont = new Font(Font.SERIF,Font.BOLD,14);
		
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
		buttons.setBackground(SudokuGrid.BACKGROUND_COLOR);
		
		solve = new JButton("Decipher");
		solve.setFont(buttonFont);
		solve.setBorder(buttonBorder);
		solve.setPreferredSize(buttonSize);
		solve.addActionListener(new ButtonListener());
		
		clear = new JButton("Clear");
		clear.setFont(buttonFont);
		clear.setBorder(buttonBorder);
		clear.setPreferredSize(buttonSize);
		clear.addActionListener(new ButtonListener());
		
		buttons.add(clear);
		buttons.add(solve);
	}
	
	public BufferedImage getIcon() {
		BufferedImage sudokuIcon = null;
		try {
		    sudokuIcon = ImageIO.read(new File("aziz_icon.png"));
		} 
		catch (IOException e) {}
		return sudokuIcon;
	}
	
	public void display() {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		this.pack();
		this.setLocationRelativeTo(null);
		//this.setResizable(false);
		this.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (((JButton) event.getSource()).getText().equals("Decipher"))
				diciperButton();
			else
				clearButton();
		}
		
		private void diciperButton() {
			JTextField[][] board = grid.getGrid();
			SudokuBoard sudokuBoard = new SudokuDecipher();
			for (int y = 0; y < board.length; y++)
				for (int x = 0; x < board[y].length; x++) {
					ArrayList<Integer> cell = new ArrayList<Integer>();
					if (board[y][x].getText().length() == 1)
						cell.add(Integer.parseInt(board[y][x].getText()));
					else
						cell.add(0);
					sudokuBoard.setCell(x, y, cell);
				}
			grid.decipherSudoku((SudokuDecipher) sudokuBoard);
		}
		
		private void clearButton() {
			for (int y = 0; y < 9; y++)
				for (int x = 0; x < 9; x++)
					grid.setCellString(x, y, "");
		}
		
	}
	

}
