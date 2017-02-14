package sudoku;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * 
 * @author Aziz
 * @version 7/21/2010: Solve is the default button and the buttons Clear and Solve
 * 		are removed from the focus cycle.
 * 			8/6/2010: Methods displayText() and getSudokuGrid() were added.
 *
 */
public class SudokuFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private final static String TITLE 	= "Sudoku Decipher                   Copyright © 2010 Aziz Javed";
	private SudokuGrid grid;
	private javax.swing.JPanel buttons, messages;
	private JButton solve,clear;
	private JLabel difficultyLevelMessage, resultMessage;
	
	public SudokuFrame() {
		super(TITLE);
		this.setIconImage(getIcon());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		grid = new SudokuGrid();
		computeButtons();
		
		this.getContentPane().add(grid,java.awt.BorderLayout.CENTER);
		this.getContentPane().add(buttons,java.awt.BorderLayout.PAGE_END);
	}
	
	private void computeButtons() {
		javax.swing.border.Border buttonBorder = javax.swing.BorderFactory.
			createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED);
		Dimension buttonSize = new Dimension(90,30);
		Font buttonFont = new Font(Font.SERIF,Font.BOLD,14);
		
		buttons = new javax.swing.JPanel();
		buttons.setLayout(new javax.swing.BoxLayout(buttons,javax.swing.BoxLayout.X_AXIS));
		buttons.setBackground(SudokuGrid.BACKGROUND_COLOR);
		
		solve = new JButton("Decipher");
		solve.setFont(buttonFont);
		solve.setBorder(buttonBorder);
		solve.setPreferredSize(buttonSize);
		solve.addActionListener(new ButtonListener());
		solve.setAlignmentY(Component.TOP_ALIGNMENT);
		solve.setFocusable(false);
		this.getRootPane().setDefaultButton(solve);
		
		clear = new JButton("Clear");
		clear.setFont(buttonFont);
		clear.setBorder(buttonBorder);
		clear.setPreferredSize(buttonSize);
		clear.addActionListener(new ButtonListener());
		clear.setAlignmentY(Component.TOP_ALIGNMENT);
		clear.setFocusable(false);
	
		resultMessage = new JLabel("N/A");
		resultMessage.setFont(new Font(Font.SERIF,Font.PLAIN,14));
		resultMessage.setForeground(SudokuGrid.BACKGROUND_COLOR);
		resultMessage.setPreferredSize(new Dimension(200,10));
		resultMessage.setAlignmentY(Component.TOP_ALIGNMENT);
		
		difficultyLevelMessage = new JLabel("N/A");
		difficultyLevelMessage.setFont(new Font(Font.SERIF,Font.PLAIN,14));
		difficultyLevelMessage.setForeground(SudokuGrid.BACKGROUND_COLOR);
		difficultyLevelMessage.setPreferredSize(new Dimension(200,5));
		difficultyLevelMessage.setAlignmentY(Component.TOP_ALIGNMENT);
		
		messages = new javax.swing.JPanel();
		messages.setLayout(new java.awt.GridLayout(0,1,0,0));
		messages.add(resultMessage);
		messages.add(difficultyLevelMessage);
		messages.add(new JLabel());
		messages.setPreferredSize(new Dimension(200,45));
		messages.setAlignmentY(Component.TOP_ALIGNMENT);
		messages.setOpaque(false);
		
		buttons.add(javax.swing.Box.createRigidArea(new Dimension(80,20)));
		buttons.add(clear);
		buttons.add(javax.swing.Box.createRigidArea(new Dimension(15,20)));
		buttons.add(solve);
		buttons.add(javax.swing.Box.createRigidArea(new Dimension(50,0)));
		buttons.add(messages);
	}
	
	public SudokuGrid getSudokuGrid() {
		return grid;
	}
	
	public void displayText(String message, int lineNumber) {
		if (lineNumber == 1) {
			resultMessage.setText(message);
			resultMessage.setForeground(java.awt.Color.WHITE);
		}
		else {
			difficultyLevelMessage.setText(message);
			difficultyLevelMessage.setForeground(java.awt.Color.WHITE);
		}

	}
	
	private java.awt.image.BufferedImage getIcon() {
		java.awt.image.BufferedImage sudokuIcon = null;
		java.net.URL imageURL = SudokuFrame.class.getResource("aziz_icon.png");
		try {
			if (imageURL != null)
				sudokuIcon = javax.imageio.ImageIO.read(imageURL);
		} 
		catch (java.io.IOException e) {}
		return sudokuIcon;
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(java.awt.event.ActionEvent event) {
			if (((JButton) event.getSource()).getText().equals("Decipher"))
				diciperButton();
			else
				clearButton();
		}
		
		private void diciperButton() {
			javax.swing.JTextField[][] board = grid.getGrid();
			SudokuBoard sudokuBoard = new SudokuDecipher();
			for (int y = 0; y < board.length; y++)
				for (int x = 0; x < board[y].length; x++) {
					java.util.ArrayList<Integer> cell = new java.util.ArrayList<Integer>();
					if (board[y][x].getText().length() == 1)
						cell.add(Integer.parseInt(board[y][x].getText()));
					else
						cell.add(0);
					sudokuBoard.setCell(x, y, cell);
				}
			int result = grid.decipherSudoku((SudokuDecipher) sudokuBoard);
			displayResult(result);
		}
		
		private void clearButton() {
			for (int y = 0; y < SudokuBoard.ROW; y++)
				for (int x = 0; x < SudokuBoard.COLUMN; x++)
					grid.resetCell(x, y);
			resultMessage.setForeground(SudokuGrid.BACKGROUND_COLOR);
			difficultyLevelMessage.setForeground(SudokuGrid.BACKGROUND_COLOR);
		}
		
		private void displayResult(int result) {
			if (result == SudokuGrid.INVALID)
				resultMessage.setText("Board is invalid.");
			if (result == SudokuGrid.ENIGMA)
				resultMessage.setText("Unable to compute solution.");
			if (result == SudokuGrid.COMPLETE)
				resultMessage.setText("Unique solution found!");
			resultMessage.setForeground(java.awt.Color.WHITE);
			difficultyLevelMessage.setText("Difficulty Level: " +String.valueOf(grid.getDifficultyLevel()));
			difficultyLevelMessage.setForeground(java.awt.Color.WHITE);
		}
	}
}
