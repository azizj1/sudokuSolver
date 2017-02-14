import java.util.ArrayList;


//17 Methods + 2 Constructors
@SuppressWarnings("unchecked")
public class SudokuBoard {
	
	private ArrayList<Integer>[][] initialBoard, board;
	protected final static int ROW = 9, COLUMN = 9;
	public final static int EMPTY = 0;
	
	public SudokuBoard() {
		initialBoard = new ArrayList[ROW][COLUMN];
		board = new ArrayList[ROW][COLUMN];
		for (int y = 0; y < board.length; y++)
			for (int x = 0; x < board[y].length; x++) {
				ArrayList<Integer> cellInitial = new ArrayList<Integer>();
				ArrayList<Integer> cellFinal = new ArrayList<Integer>();
				cellInitial.add(0);
				cellFinal.add(0);
				board[y][x] = cellFinal;
				initialBoard[y][x] = cellInitial;
			}
	}
	
	public SudokuBoard(ArrayList<Integer>[][] initialBoard) {
		if (initialBoard.length != ROW || initialBoard[0].length != COLUMN)
			throw new InValidGridException("ROW = " + initialBoard.length + "  COLUMN = " + initialBoard[0].length);
		this.initialBoard = initialBoard;
		board = initialBoard;
	}
	
	public ArrayList<Integer>[][] getInitialBoard() {
		return initialBoard;
	}
	
	public ArrayList<Integer>[][] getBoard() {
		return board;
	}
	
	public void setBoard(ArrayList<Integer>[][] board) {
		if (board.length != ROW || board[0].length != COLUMN)
			throw new InValidGridException("ROW = " + board.length + "  COLUMN = " + board[0].length);
		this.board = board;
	}
	/**
	 * 
	 * @param row an int. 
	 * @return If 0 <= row <= 8, return an ArrayList<Integer>[]. else, throw OutOfGridException
	 */
	public ArrayList<Integer>[] getCellsInRow(int row) {
		if (row >= 9 || row < 0)
			throw new OutOfGridException("A row can only be 0 <= row <=8. ROW = " + row);
		return board[row];
	}
	
	/**
	 * 
	 * @param col an int. 
	 * @return If 0 <= col <= 8, return an ArrayList<Integer>[]. else, throw OutOfGridException
	 */
	public ArrayList<Integer>[] getCellsInCol(int col) {
		if (col >= 9 || col < 0)
			throw new OutOfGridException("A column can only be 0 <= col <=8. COL = " + col);
		ArrayList<Integer> colArray[] = new ArrayList[ROW];
		for (int k = 0; k < colArray.length; k++)
			colArray[k] = board[k][col];
		return colArray;
	}
	/**
	 * 
	 * @param region = region 0 is the top left 3x3 box. Regions go from
	 * 		left to right. 
	 * @return an ArrayList<Integer> of all the contents in int region. 0 is equivalent to
	 * 		empty. First element is top right number. Numbers go from right to
	 * 		left in the region. 
	 */
	public ArrayList<Integer>[] getCellsInRegion(int region) {
		if (region >= 9 || region < 0)
			throw new OutOfGridException("A region can only be 0 <= region <=8. REGION = " + region);
		int Xi = (region % 3) * 3, Yi = region * 3 / 9 * 3;
		ArrayList<Integer> regionArray[] = new ArrayList[(ROW / 3) * (COLUMN / 3)];
		
		for (int k = 0; k < regionArray.length; k++)
			regionArray[k] = board[Yi + (k/3)][Xi + (k%3)];
		return regionArray;
	}
	
	/**
	 * 
	 * @param region The first region is 0. Last region is 8. Regions go from left to right.
	 * @param index The index of the location if the region was put in an array.
	 * @return The first digit is the x-coordinate. The second digit is the y-coordinate.
	 * 		If only one digit, the x-coordinate is 0 and y-coordinate is the digit. 
	 */
	public int findLocInRegion(int region, int index) {
		if ((region < 0 || region >= 9) || (index < 0 || index >=9))
			throw new OutOfGridException("REGION = " + region + "  INDEX = " + index);
		int Xi = (region % 3) * 3, Yi = region * 3 / 9 * 3;
		int Xf = Xi + (index % 3), Yf = Yi + (index / 3);
		return Xf*10 + Yf;
	}
	
	public int getRegion(int col, int row) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		return (col/3 + row/3*3);
	}
	
	/**
	 * 
	 * @param cell self-explanatory
	 * @return an ArrayList<Integer> of decisive numbers.
	 */
	protected ArrayList<Integer> getDecisiveNumbers(ArrayList<Integer>[] cell) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int k = 0; k < cell.length; k++)
			if (cell[k].size() == 1 && cell[k].get(0).intValue() != EMPTY)
				list.add(cell[k].get(0));
		return list;
	}
	
	public boolean isCellEmpty(int col, int row) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		return (board[row][col] == null || (board[row][col].size() == 1 && board[row][col].get(0) == EMPTY));
	}
	
	
	public ArrayList<Integer> getCell(int col, int row) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		return board[row][col];
	}
	
	public ArrayList<Integer> removeCell(int col, int row) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		if (board[row][col] == null)
			return null;
		
		ArrayList<Integer> newCell = new ArrayList<Integer>();
		newCell.add(EMPTY);
		board[row][col] = newCell;
		
		return board[row][col];
	}
	
	public void setCell(int col, int row, ArrayList<Integer> cell) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		if (cell.size() > 9)
			throw new InValidNumberException("CELL SIZE = " + cell.size());
		board[row][col] = cell;
	}
	
	public void appendToCell(int col, int row, int value) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		if (value > 9 || value <= 0)
			throw new InValidNumberException("VALUE = " + value);
		if (!board[row][col].contains(value))
			board[row][col].add(value);
	}
	
	public boolean removeFromCell(int col, int row, int value) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		if (value > 9 || value < 0)
			throw new InValidNumberException("VALUE = " + value);
		if (board[row][col].contains(value)) {
			board[row][col].remove(board[row][col].indexOf(value));
			return true;
		}
		return false;
	}
	
	public String toString() {
		String result = "   |  0         1         2         3         4         5         6         7         8" +
				"         \n---+--------------------------------------------------------------------------------------------\n";
		String alphabets[] = {"0","1","2","3","4","5","6","7","8"};
		for (int y = 0; y < SudokuBoard.ROW; y++) {
			result+= alphabets[y] + "  |  ";
			for (int x = 0; x < SudokuBoard.COLUMN; x++) {
				int cell = 0;
				for (int z = 0; z < getCell(x,y).size(); z++)
					cell = cell*10 + getCell(x,y).get(z);
				String cellString = String.valueOf(cell);
				String spaces = "";
				for (int k = 0; k <= 9 - cellString.length(); k++)
					spaces+= " ";
				result+= cellString + spaces;
			}

			result+= "\n";
		}
		return result;
	}
	/**
	 * Two SudokuBoards are equal if every cell in the board is equal.
	 */
	public boolean equals(Object o) {
		if (o instanceof SudokuBoard) {
			SudokuBoard analogous = (SudokuBoard) o;
			for (int y = 0; y < ROW; y++)
				for (int x = 0; x < COLUMN; x++)
					if (!this.board[y][x].equals(analogous.board[y][x])) 
						return false;

			return true;
		}
		return false;
	}
}
