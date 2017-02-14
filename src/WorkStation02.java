
public class WorkStation02 extends DriverHelperMethods {

	public static void main(String[] args) {
		long initialTime = System.currentTimeMillis();
		
		//SudokuBoard boards[] = getBoards();
		//ArrayList<Integer> board[][] = boards[0].getBoard();
		//ArrayList<Integer> board1[][] = boards[1].getBoard();
		//ArrayList<Integer> board2[][] = boards[2].getBoard();
		
		long interludeTime = System.currentTimeMillis();
		System.out.printf("It took %d ms to initialize the setup.%n", interludeTime - initialTime);

		/**
		 * SudokuAnalysis was deleted. All its method were implemented in SudokuSolver.
		 */
		//SudokuAnalysis test = new SudokuAnalysis(board);
		//test.removeCell(0,0);
		//test.removeCell(5,2);
		//printBoard(test.getBoard());
		//System.out.println(test.isComplete());
		System.out.printf("It took %d ms to test for completion the setup.%n",System.currentTimeMillis() - interludeTime);


		
	}

}
