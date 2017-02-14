import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class WorkStation03 extends DriverHelperMethods {
	
	public static void main(String[] args) {
		/*
		SudokuBoard boards[] = getBoards();
		ArrayList<Integer> board[][] = boards[0].getBoard();
		ArrayList<Integer> board1[][] = boards[1].getBoard();
		ArrayList<Integer> board2[][] = boards[2].getBoard();
		
		System.out.println(boards[2].getDecisiveNumbers(boards[2].getRegion(1)));
		*/

		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				int xp = x, yp = y;
				if (x - 1 == -1) {
					xp = 8;
					yp = y-1;
				}
				System.out.printf("Current region: %d; previous region: %d; x = %d; y = %d.%n",x/3 + y/3*3,(xp-1)/3 + yp/3*3, x, y);
			}
		}
		
		ArrayList<Integer> arr[] = new ArrayList[10];
		System.out.println(arr[0]);
	}

}
