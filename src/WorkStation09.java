import java.util.ArrayList;


public class WorkStation09 extends DriverHelperMethods {


	public static void main(String[] args) {
		/*
		SudokuBoard boards[] = getBoards();
		SudokuDecipher test1 = new SudokuDecipher(boards[8].getBoard());
		System.out.printf("Initial(%b)%n%s%n",test1.isComplete(),test1.toString());
		long initialTime = System.currentTimeMillis();
		System.out.println(test1.compute());
		System.out.printf("It took %d ms to process everything.%n",System.currentTimeMillis() - initialTime);
		System.out.printf("After compute%n%s%n",test1.toString());
		System.out.println(test1.getDifficultyLevel());
		*/
		
		SudokuBoard board = new SudokuDecipher();
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++) {
				ArrayList<Integer> cell = new ArrayList<Integer>();
				cell.add(x+y);
				board.setCell(x,y,cell);
			}
		SudokuDecipher solve = (SudokuDecipher) board;

	}

}
