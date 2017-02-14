
public class WorkStation05 extends DriverHelperMethods {

	public static void main(String[] args) {
		SudokuBoard boards[] = getBoards();
		SudokuDecipher test1 = new SudokuDecipher(boards[4].getBoard());

		System.out.printf("Initial(%b)%n%s%n",test1.isComplete(),test1.toString());
		//long initialTime = System.currentTimeMillis();
		System.out.println(test1.compute());

		System.out.printf("After compute%n%s%n",test1.toString());


		System.out.println(test1.isComplete());
		/*
		System.out.println(test1.compute());
		System.out.printf("After compute:%n%s%n",test1.toString());
		System.out.println(test1.isComplete());
		System.out.println(test1.getDecisiveString(test1.getCellsInCol(0)));
		
		/*
		System.out.println(test1.getDecisiveString(test1.getCellsInRow(0)));
		for (int k = 0; k < 9; k++) {
			String possibleRegionString = (test1.findAssociatedRegion
					(k,0) < 100) ? "0" + String.valueOf(test1.findAssociatedRegion
					(k,0)) : String.valueOf(test1.findAssociatedRegion
							(k,0));
					System.out.println(possibleRegionString);
		}
		ArrayList<Integer> list = test1.findNumXLocs(test1.getCellsInRow(0),4,0,1);
		for (int k = 0; k < list.size(); k++)
			System.out.printf("Locations of x when x = 4 in row 0: (%d,%d).%n",list.get(k) / 10,list.get(k) % 10);
		/*
		test1.inflateBoard();
		System.out.printf("After inflateBoard:%n%s%n",test1.toString());
		ArrayList<Integer> locs = test1.getIndecisiveCellsLoc(test1.getCellsInRow(2),1,2);
		for (int k = 0; k < locs.size(); k++)
			System.out.printf("Index %d: (%d,%d)%n", k,locs.get(k)/10,locs.get(k)%10);
		
		/*
		System.out.printf("Initial:%n%s%n",test1.toString());
		test1.inflateBoard();
		System.out.printf("After inflateBoard:%n%s%n",test1.toString());
		test1.compute();
		System.out.printf("After compute:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		test1.trim();
		System.out.printf("After trim:%n%s%n",test1.toString());
		test1.alleviate();
		System.out.printf("After alleviate:%n%s%n",test1.toString());
		/*
		int arr[] = test1.getIndecisiveNumbers(test1.getCellsInRow(1));
		ArrayList<Integer> arrlist[] = test1.getCellsInRow(1);
		
		/*
		for (int k = 0; k < arr.length; k++) {
			System.out.printf("The sudoku number %d occurs %d many times.%n",(k+1),arr[k]);
			System.out.printf("Row #1 element #%d: %s%n",k,arrlist[k]);
		}
		*/
		

	}

}
