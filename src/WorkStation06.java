public class WorkStation06 extends DriverHelperMethods {

	public static void main(String[] args) {
		SudokuBoard boards[] = getBoards();
		SudokuDecipher test1 = new SudokuDecipher(boards[4].getBoard());
		long initialTime = System.currentTimeMillis();
		System.out.printf("Initial(%b)%n%s%n",test1.isComplete(),test1.toString());
		System.out.println(test1.compute());
		System.out.printf("After compute%n%s%n",test1.toString());
		//ArrayList<ArrayList<Integer>> test[] = test1.getCongruentNumbs(test1.getCellsInRow(7),7,1);
		//for (int k = 0; k < test.length; k++)
			//System.out.println(test[k]);
		System.out.printf("Complete? %b;%n",test1.isComplete());
		System.out.printf("It took %d ms to process everything.%n",System.currentTimeMillis() - initialTime);
		/*
		ArrayList<Integer> customCol[] = new ArrayList[9];
		ArrayList<Integer> cell1 = new ArrayList<Integer>(),cell2 = new ArrayList<Integer>(),
		cell3 = new ArrayList<Integer>(),cell4 = new ArrayList<Integer>(),cell5 = new ArrayList<Integer>(),
		cell6 = new ArrayList<Integer>(),cell7 = new ArrayList<Integer>(),cell8 = new ArrayList<Integer>(),
		cell9 = new ArrayList<Integer>();
		cell1.add(2); cell1.add(3); cell1.add(6); cell1.add(9);
		cell2.add(2); cell2.add(3); cell2.add(9);
		cell3.add(6);
		cell4.add(5); cell4.add(6); cell4.add(4);
		cell5.add(2); cell5.add(3); cell5.add(5); cell5.add(9);
		cell6.add(4); cell6.add(5); cell6.add(8);
		cell7.add(4); cell7.add(6); cell7.add(7);
		cell8.add(4); cell8.add(7);
		cell9.add(1);
		customCol[0] = cell1; customCol[1] = cell2; customCol[2] = cell3; customCol[3] = cell4; 
		customCol[4] = cell5; customCol[5] = cell6; customCol[6] = cell7; customCol[7] = cell8; 
		customCol[8] = cell9;


		ArrayList<ArrayList<Integer>> test[] = test1.getCongruentNumbs(test1.getCellsInCol(1),1,0);

		for (int k = 0; k < test.length; k++)
			System.out.println(test[k]);
		*/
		

		
		

	}

}
