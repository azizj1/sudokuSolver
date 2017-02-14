public class WorkStation04 extends DriverHelperMethods {

	public static void main(String[] args) {
		
		SudokuBoard boards[] = getBoards();
		SudokuDecipher test1 = new SudokuDecipher(boards[4].getBoard());
		long initialTime = System.currentTimeMillis();
		System.out.println(test1.toString());
		//System.out.println(test1.equals(boards[3].getBoard()));
		
		System.out.println(test1.compute());
		
		/*
		test1.inflateBoard();
		System.out.println(test1.toString());
		test1.trim();
		test1.trim();
		test1.trim();
		test1.trim();
		/*
		test1.cleanse();
		test1.cleanse();
		test1.cleanse();
		test1.cleanse();
		test1.cleanse();
		test1.cleanse();
		test1.cleanse();
		test1.cleanse();

		test1.cleanse();
		test1.cleanse();
		/*
		test1.inflateBoard();
		test1.inflateBoard();
		test1.inflateBoard();
		test1.inflateBoard();
		test1.inflateBoard();
		test1.inflateBoard();
		test1.inflateBoard();
		*/
		System.out.printf("It took %d ms to inflate and clean board.%n",System.currentTimeMillis() - initialTime);
		//SudokuAnalysis test2 = new SudokuAnalysis(test1.getBoard());
		//System.out.println(test2.)
		System.out.println(test1.toString());

		
	}

}
