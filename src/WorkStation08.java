import java.util.ArrayList;


public class WorkStation08 extends DriverHelperMethods {

	public static void main(String[] args) {
		SudokuBoard boards[] = getBoards();
		SudokuDecipher test1 = new SudokuDecipher(boards[13].getBoard());
		long initialTime = System.currentTimeMillis();
		//System.out.printf("Initial(%b)%n%s%n",test1.isComplete(),test1.toString());
		//System.out.println(test1.compute());
		//System.out.printf("After compute%n%s%n",test1.toString());
		//ArrayList<ArrayList<Integer>> test[] = test1.getCongruentNumbs(test1.getCellsInRow(7),7,1);
		//for (int k = 0; k < test.length; k++)
			//System.out.println(test[k]);
		//System.out.printf("Complete? %b;%n",test1.isComplete());
		SudokuBoard test = new SudokuBoard();
		SudokuDecipher test2 = new SudokuDecipher(test.getBoard());
		System.out.printf("It took %d ms to process everything.%n",System.currentTimeMillis() - initialTime);
		ArrayList<Integer> customCol[] = new ArrayList[9];
		ArrayList<Integer> cell1 = new ArrayList<Integer>(),cell2 = new ArrayList<Integer>(),
		cell3 = new ArrayList<Integer>(),cell4 = new ArrayList<Integer>(),cell5 = new ArrayList<Integer>(),
		cell6 = new ArrayList<Integer>(),cell7 = new ArrayList<Integer>(),cell8 = new ArrayList<Integer>(),
		cell9 = new ArrayList<Integer>();
		cell1.add(5); cell1.add(6);
		cell2.add(3); cell2.add(4); cell2.add(8);
		cell3.add(2); cell3.add(3); cell3.add(5); cell3.add(8);
		cell4.add(3); cell4.add(4);
		cell5.add(2); cell5.add(6);
		cell6.add(9);
		cell7.add(4); cell7.add(7);
		cell8.add(1);
		cell9.add(3); cell9.add(7);
		customCol[0] = cell1; customCol[1] = cell2; customCol[2] = cell3; customCol[3] = cell4; 
		customCol[4] = cell5; customCol[5] = cell6; customCol[6] = cell7; customCol[7] = cell8; 
		customCol[8] = cell9;

		for (int k = 0; k < customCol.length; k++)
			System.out.println(customCol[k]);


		test2.exploitChainsInArray(customCol,1,0);
		for (int k = 0; k < customCol.length; k++)
			System.out.println(customCol[k]);

	}

}
