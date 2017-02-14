import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class WorkStation01 extends DriverHelperMethods {
		
	public static void main(String[] args) {
			
		ArrayList<Integer> board1[][] = new ArrayList[9][9];
			
		long initialTime = System.currentTimeMillis();
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++) {
				ArrayList<Integer> test = new ArrayList<Integer>();
				for (int z = 0; z < 25; z++)
					test.add(z);
				board1[y][x] = test;
			}
		long interludeTime = System.currentTimeMillis();
		System.out.printf("It took %d ms to fill the 3D system.%n", interludeTime - initialTime);
		printArray(board1[0]);
		long interlude1Time = System.currentTimeMillis();
		System.out.printf("It took %d ms to print an array of the 3D system.%n", interlude1Time - interludeTime);
		printBoard(board1);
		System.out.printf("It took %d ms to print the 3D system.%n", System.currentTimeMillis() - interlude1Time);	
	}
}
