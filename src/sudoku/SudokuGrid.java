package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DocumentFilter;
import javax.swing.text.TextAction;

/**
 * 
 * @author Aziz
 * @version 7/26/2010: Added the ability to traverse through the board using 
 * 		arrow keys.
 * 			8/5/2010: Methods setGrid() and generatePropertyValue() were added.
 */
public class SudokuGrid extends JPanel {

	private static final long serialVersionUID 	= 1L;
	private static final int CELL_CONSTANT 		= 20;
	private static final Font CELL_FONT 		= new Font(Font.DIALOG,Font.BOLD,CELL_CONSTANT);
	private static final Color FONT_COLOR		= Color.BLUE;
	
	private final int CELL_SIDE 				= CELL_CONSTANT * 5 / 2;
	private final java.awt.Dimension CELL_SIZE	= new java.awt.Dimension(CELL_SIDE,CELL_SIDE);
	private final int GAP						= 5;
	
	public static final Color BACKGROUND_COLOR	= new Color(4,7,117);
	public static final int INVALID 			= -1, ENIGMA = 0, COMPLETE = 1;

	private final JTextField[][] grid 			= new JTextField[SudokuBoard.ROW][SudokuBoard.COLUMN];
	private int difficultyLevel;
	
	public SudokuGrid() {
		
		difficultyLevel = -1;
		
		//Polish up the SudokuGrid.
		this.setBackground(BACKGROUND_COLOR);
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
		this.setLayout(new GridLayout(3,3,GAP,GAP));
		
		//When tab is pressed, the cursor should go horizontal until it reaches the end of a row.
		//Therefore, there are 3 JPanels in the SudokuGrid, one for three regions aligned horizontally.
		//Each JPanel stated in the prior comment has an inset with three JPanels. Each JPanel represents
		//		a row of SudokuGrid.
		//Another level deeper, each JPanel stated in the prior comment has an inset of three JPanels. Each
		//		JPanel represents three cells.
		//This method was used to keep the flow of tabs as stated in the first comment.
		JPanel[] threeRegionsInGrid = new JPanel[3];
		JPanel[] threeRowsInRegion = new JPanel[3];
		JPanel[] threeCellsInRow = new JPanel[3];
		
		//Filter that forces a JTextField to only have ONE number from one through nine.
		DocumentSudokuFilter filter = new DocumentSudokuFilter();
		
		//Enables the client to traverse through the grid using the arrow keys.
		javax.swing.Action rightArrow = new SudokuTraversalPolicy("RIGHT", 1, 0), 
				leftArrow = new SudokuTraversalPolicy("LEFT", -1, 0), 
				upArrow = new SudokuTraversalPolicy("UP", 0, -1), 
				downArrow = new SudokuTraversalPolicy("DOWN", 0, 1);

		for (int x = 0; x < 3; x++) {
			threeRegionsInGrid[x] = new JPanel();
			threeRegionsInGrid[x].
				setLayout(new javax.swing.BoxLayout(threeRegionsInGrid[x],
						javax.swing.BoxLayout.PAGE_AXIS));
			for (int y = 0; y < 3; y++) {
				threeRowsInRegion[y] = new JPanel();
				threeRowsInRegion[y].setLayout(new GridLayout(1,3,GAP,0));
				threeRowsInRegion[y].setBackground(BACKGROUND_COLOR);
				for (int x_2 = 0; x_2 < 3; x_2++) {
					threeCellsInRow[x_2] = new JPanel();
					threeCellsInRow[x_2].setLayout(new GridLayout(1,3,0,0));
					for (int y_2 = 0; y_2 < 3; y_2++) {
						JTextField cell = new JTextField(3);
						cell.setPreferredSize(CELL_SIZE);
						cell.setHorizontalAlignment((int) java.awt.Component.CENTER_ALIGNMENT);
						cell.setFont(CELL_FONT);
						((javax.swing.text.AbstractDocument) cell.getDocument())
							.setDocumentFilter(filter);
						grid[y + 3*x][y_2 + 3*x_2] = cell;
						cell.setName("" + (y_2 + 3*x_2) + (y + 3*x));
						
						javax.swing.InputMap im = cell.getInputMap();
						im.put(KeyStroke.getKeyStroke("RIGHT"), "R");
						im.put(KeyStroke.getKeyStroke("LEFT"), "L");
						
						javax.swing.ActionMap am = cell.getActionMap();
						am.put("R", rightArrow);
						am.put("L", leftArrow);
						
						threeCellsInRow[x_2].add(cell);
					}
					threeRowsInRegion[y].add(threeCellsInRow[x_2]);
				}
				threeRegionsInGrid[x].add(threeRowsInRegion[y]);
			}
			this.add(threeRegionsInGrid[x]);
		}
		javax.swing.InputMap im = this.getInputMap(javax.swing.JComponent
				.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		javax.swing.ActionMap am = this.getActionMap();
		im.put(KeyStroke.getKeyStroke("UP"), "U");
		im.put(KeyStroke.getKeyStroke("DOWN"), "D");
		
		am.put("U", upArrow);
		am.put("D", downArrow);
	}
	
	public JTextField[][] getGrid() {
		return grid;
	}
	
	public void setGrid(String propertyValue) {
		if (propertyValue.length() != 81)
			return;
		char[] values = propertyValue.toCharArray();
		for (int y = 0; y < SudokuBoard.ROW; y++)
			for (int x = 0; x < SudokuBoard.COLUMN; x++)
				setCellString(x, y, values[9*y + x] == '0' ? "" : String.valueOf(values[9*y + x]));
	}
	/**
	 * @return This is the key that will be used with Properties class.
	 */
	public String generatePropertyValue() {
		String value = "";
		
		for (int y = 0; y < SudokuBoard.ROW; y++)
			for (int x = 0; x < SudokuBoard.COLUMN; x++)
				value += (getCellString(x, y).length() == 0) ? SudokuBoard.EMPTY 
						: getCellString(x, y);
		return value;
	}
	
	public boolean isEmpty() {
		for (int y = 0; y < SudokuBoard.ROW; y++)
			for (int x = 0; x < SudokuBoard.COLUMN; x++)
				if (getCellString(x, y).length() != 0)
					return false;
		return true;
	}
	
	public void setCellString(int col, int row, String value) {
		grid[row][col].setText(value);
	}
	
	public String getCellString(int col, int row) {
		return grid[row][col].getText();
	}
	
	public void resetCell(int col, int row) {
		grid[row][col].setText("");
		grid[row][col].setForeground(Color.BLACK);
	}
	private void setDifficultyLevel(int lvl) {
		difficultyLevel = lvl;
	}
	
	public int getDifficultyLevel() {
		if (difficultyLevel < 0)
			return -1;
		return difficultyLevel;
	}
	
	public int decipherSudoku(SudokuDecipher board) {
		if (!board.isValid())
			return INVALID;
		try {
			board.compute();
			setDifficultyLevel(board.getDifficultyLevel());
		}
		catch (InValidGridException e) {
			return INVALID;
		}
		if (!board.isComplete())
			return ENIGMA;
		for (int y = 0; y < SudokuBoard.ROW; y++)
			for (int x = 0; x < SudokuBoard.COLUMN; x++) {
				if (getCellString(x,y).length() != 0)
					continue;
				setCellString(x,y,String.valueOf(board.getCell(x, y).get(0)));
				grid[y][x].setForeground(FONT_COLOR);
			}
		return COMPLETE;
	}
	
	private class DocumentSudokuFilter extends DocumentFilter {
		
		private final int MAX_CHARS = 1;
		
		public void insertString(DocumentFilter.FilterBypass fb, int offset, 
				String string, javax.swing.text.AttributeSet attr) 
		throws javax.swing.text.BadLocationException {
			if ((fb.getDocument().getLength() + string.length() <= MAX_CHARS && 
					string.matches("[1-9]")) || string.length() == 0)
				super.insertString(fb, offset, string, attr);
			else
				java.awt.Toolkit.getDefaultToolkit().beep();
		}
		public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, 
				javax.swing.text.AttributeSet attrs)
			throws javax.swing.text.BadLocationException {
			if ((fb.getDocument().getLength() + text.length() <= MAX_CHARS && 
					text.matches("[1-9]")) || text.length() == 0)
				super.replace(fb, offset, length, text, attrs);
			else
				java.awt.Toolkit.getDefaultToolkit().beep();
		}
	}
	
	@SuppressWarnings("serial")
	private class SudokuTraversalPolicy extends TextAction {

		private int x, y;
		
		public SudokuTraversalPolicy(String arrowKey, int x, int y) {
			super(arrowKey);
			this.x = x;
			this.y = y;
		}
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JTextField test = (JTextField) this.getFocusedComponent();
			int dX = Integer.parseInt(test.getName()) / 10 + x,
				dY = Integer.parseInt(test.getName()) % 10 + y,
				row = (dY >= 0 && dY < SudokuBoard.ROW) ? dY : (y == 1) ? 0 : 8,
				column = (dX >= 0 && dX < SudokuBoard.COLUMN) ? dX : (x == 1) ? 0 : 8;
			grid[row][column].requestFocusInWindow();
		}
	}
}
