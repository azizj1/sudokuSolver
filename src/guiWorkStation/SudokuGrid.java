package guiWorkStation;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

import sudoku.SudokuDecipher;


public class SudokuGrid extends JPanel {
	
	private static final int CELL_CONSTANT 		= 20, COLUMN = 9, ROW = 9;
	public static final Font CELL_FONT 			= new Font(Font.DIALOG,Font.BOLD,CELL_CONSTANT);
	public static final Color BACKGROUND_COLOR	= new Color(4,7,117);
	
	private final int CELL_SIDE 				= CELL_CONSTANT * 5 / 2;
	private final Dimension CELL_SIZE			= new Dimension(CELL_SIDE,CELL_SIDE);
	private final int GAP						= 5;
	
	private JTextField[][] grid 				= new JTextField[9][9];
	
	public SudokuGrid() {
		
		//Polish up the SudokuGrid.
		this.setBackground(BACKGROUND_COLOR);
		this.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
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
		
		for (int x = 0; x < 3; x++) {
			threeRegionsInGrid[x] = new JPanel();
			threeRegionsInGrid[x].
				setLayout(new BoxLayout(threeRegionsInGrid[x],BoxLayout.PAGE_AXIS));
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
						cell.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
						cell.setFont(CELL_FONT);
						((AbstractDocument) cell.getDocument()).setDocumentFilter(filter);
						grid[y + 3*x][y_2 + 3*x_2] = cell;
						
						threeCellsInRow[x_2].add(cell);
					}
					threeRowsInRegion[y].add(threeCellsInRow[x_2]);
				}
				threeRegionsInGrid[x].add(threeRowsInRegion[y]);
			}
			this.add(threeRegionsInGrid[x]);
		}
	}
	
	public JTextField[][] getGrid() {
		return grid;
	}
	
	public void setCellString(int col, int row, String value) {
		grid[row][col].setText(value);
	}
	
	public String getCellString(int col, int row) {
		return grid[row][col].getText();
	}
	
	public void decipherSudoku(SudokuDecipher board) {
		if (!board.isValid())
			return;
		board.compute();
		if (!board.isComplete())
			return;
		for (int y = 0; y < ROW; y++)
			for (int x = 0; x < COLUMN; x++) {
				if (getCellString(x,y).length() != 0)
					continue;
				setCellString(x,y,String.valueOf(board.getCell(x, y).get(0)));
				grid[y][x].setForeground(Color.red);
			}
	}
	
	private class DocumentSudokuFilter extends DocumentFilter {
		
		private final int MAX_CHARS = 1;
		
		public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) 
			throws BadLocationException {
			if ((fb.getDocument().getLength() + string.length() <= MAX_CHARS && string.matches("[1-9]")) || string.length() == 0)
				super.insertString(fb, offset, string, attr);
			else
				Toolkit.getDefaultToolkit().beep();
		}
		public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, 
							AttributeSet attrs)
			throws BadLocationException {
			if ((fb.getDocument().getLength() + text.length() <= MAX_CHARS && text.matches("[1-9]")) || text.length() == 0)
				super.replace(fb, offset, length, text, attrs);
			else
				Toolkit.getDefaultToolkit().beep();
		}
	}
}
