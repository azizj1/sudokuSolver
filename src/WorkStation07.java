
public class WorkStation07 extends DriverHelperMethods {

	public static void main(String[] args) {
		SudokuBoard boards[] = getBoards();
		SudokuDecipher test1 = new SudokuDecipher(boards[13].getBoard());
		//SudokuBoard test2 = new SudokuBoard();
		System.out.printf("Initial(%b)%n%s%n",test1.isValid(),test1.toString());
		long initialTime = System.nanoTime();
		System.out.println(test1.compute());
		System.out.printf("It took %d us to process everything.%n",(System.nanoTime() - initialTime)/1000);
		System.out.printf("After compute%n%s%n",test1.toString());
		System.out.printf("Complete? %b;%n",test1.isComplete());
		System.out.println(test1.getDifficultyLevel());
		test1.exploitChainsInArray(test1.getCellsInRegion(8),8,2);
		System.out.println(test1);

		//System.out.println(test2);
		/*
		ArrayList<Integer>[][] board = new ArrayList[2][2];
		for (int y = 0; y < board.length; y++)
			for (int x = 0; x < board[y].length; x++) {
				ArrayList<Integer> cell = new ArrayList<Integer>();
				cell.add(0);
				board[y][x] = cell;
			}
		//SudokuBoard test3 = new SudokuBoard(board);
		//test1.getCellsInRow(9);
		*/
	}
}

