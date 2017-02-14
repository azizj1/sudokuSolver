
import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class DriverHelperMethods {
		
	public static void printArray(ArrayList<Integer>[] arr) {
			
		for (ArrayList<Integer> a : arr)
			System.out.println(a);
	}
		
	public static void printBoard(ArrayList<Integer>[][] arr) {
		for (int y = 0; y < arr.length; y++)
			for (int x = 0; x < arr[y].length; x++)
				System.out.println(arr[y][x]);
	}
	
	public static SudokuBoard[] getBoards() {
		int allNumbers[] = {8,3,2,5,9,1,6,7,4
							,4,9,6,3,8,7,2,5,1
							,5,7,1,2,6,4,9,8,3
							,1,8,5,7,4,6,3,9,2
							,2,6,7,9,5,3,4,1,8
							,9,4,3,8,1,2,7,6,5
							,7,1,4,6,3,8,5,2,9
							,3,2,9,1,7,5,8,4,6
							,6,5,8,4,2,9,1,3,7};
		
		int allNumbers1[] = {1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9
							,1,2,3,4,5,6,7,8,9};
		
		int allNumbers2[] = {5,0,0,8,0,1,0,7,9
							,0,0,0,0,0,6,3,8,0
							,0,4,0,9,3,0,0,2,0
							,0,0,5,0,0,3,9,0,0
							,3,0,0,4,9,2,0,0,8
							,0,0,2,5,0,0,6,0,0
							,0,5,0,0,8,4,0,6,0
							,0,7,1,3,0,0,0,0,0
							,6,8,0,1,0,7,0,0,3};
		//"Hardest" sudoku puzzle
		int allNumbers3[] = {8,5,0,0,0,2,4,0,0
							,7,2,0,0,0,0,0,0,9
							,0,0,4,0,0,0,0,0,0
							,0,0,0,1,0,7,0,0,2
							,3,0,5,0,0,0,9,0,0
							,0,4,0,0,0,0,0,0,0
							,0,0,0,0,8,0,0,7,0
							,0,1,7,0,0,0,0,0,0
							,0,0,0,0,3,6,0,4,0};
		//#1 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers4[] = {0,0,0,2,0,0,0,6,3
							,3,0,0,0,0,5,4,0,1
							,0,0,1,0,0,3,9,8,0
							,0,0,0,0,0,0,0,9,0
							,0,0,0,5,3,8,0,0,0
							,0,3,0,0,0,0,0,0,0
							,0,2,6,3,0,0,5,0,0
							,5,0,3,7,0,0,0,0,8
							,4,7,0,0,0,1,0,0,0};
		//#2 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers5[] = {0,1,0,0,0,4,0,0,0
							,0,0,6,8,0,5,0,0,1
							,5,0,3,7,0,1,9,0,0
							,8,0,4,0,0,7,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,3,0,0,6,0,9
							,0,0,1,5,0,8,2,0,4
							,6,0,0,4,0,3,1,0,0
							,0,0,0,2,0,0,0,5,0};
		//#20 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers6[] = {1,0,0,0,7,0,0,3,0
							,8,3,0,6,0,0,0,0,0
							,0,0,2,9,0,0,6,0,8
							,6,0,0,0,0,4,9,0,7
							,0,9,0,0,0,0,0,5,0
							,3,0,7,5,0,0,0,0,4
							,2,0,3,0,0,9,1,0,0
							,0,0,0,0,0,2,0,4,3
							,0,4,0,0,8,0,0,0,9};
		//CAN'T SOLVE! (Method D) http://www.sudokusolver.co.uk/solvemethods.html
		//#4 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers7[] = {1,0,0,8,7,5,6,0,0
							,0,0,0,0,0,1,9,5,8
							,0,0,0,0,0,0,0,1,0
							,0,2,0,7,0,0,0,0,6
							,0,0,0,2,4,6,0,0,0
							,4,0,0,0,0,3,0,7,0
							,0,9,0,0,0,0,0,0,0
							,3,6,7,5,0,0,0,0,0
							,0,0,1,6,8,7,0,0,4};
		//#5 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers8[] = {0,0,2,6,0,4,0,9,3
							,0,6,0,0,2,0,4,0,0
							,5,0,4,0,0,7,0,0,0
							,2,0,3,0,0,0,0,0,0
							,0,0,8,0,0,0,6,0,0
							,0,0,0,0,0,0,1,0,8
							,0,0,0,3,0,0,7,0,5
							,0,0,7,0,4,0,0,2,0
							,8,2,0,9,0,6,3,0,0};
		//#7 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers9[] = {3,0,0,0,0,8,0,0,0
							,7,0,8,3,2,0,0,0,5
							,0,0,0,9,0,0,0,1,0
							,9,0,0,0,0,4,0,2,0
							,0,0,0,0,1,0,0,0,0
							,0,7,0,8,0,0,0,0,9
							,0,5,0,0,0,3,0,0,0
							,8,0,0,0,4,7,5,0,3
							,0,0,0,5,0,0,0,0,6};
		//#8 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbers0[] = {0,0,9,8,0,6,0,3,4
							,4,0,0,1,0,0,0,8,7
							,2,0,0,4,0,0,0,9,0
							,3,0,0,0,0,0,0,0,0
							,0,0,7,0,5,0,8,0,0
							,0,0,0,0,0,0,0,0,6
							,0,7,0,0,0,2,0,0,8
							,8,4,0,0,0,1,0,0,3
							,6,3,0,5,0,8,7,0,0};
		//#9 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbersA[] =	{0,7,0,0,3,0,0,0,8
							,0,0,2,0,0,0,0,1,4
							,1,0,8,0,0,4,7,0,0
							,0,8,0,0,0,5,0,0,0
							,0,0,7,8,1,2,9,0,0
							,0,0,0,4,0,0,0,8,0
							,0,0,5,6,0,0,3,0,9
							,7,2,0,0,0,0,6,0,0
							,6,0,0,0,4,0,0,7,0};
		//CANT SOLVE! (Method D) http://www.sudokusolver.co.uk/solvemethods.html
		//#10 from http://www.forbeginners.info/sudoku-puzzles/hard.htm
		int allNumbersB[] =	{5,3,0,4,0,0,0,0,0
							,6,9,0,8,3,0,0,0,0
							,0,1,8,0,0,0,0,0,0
							,1,6,9,0,8,0,0,0,7
							,0,2,0,0,7,0,0,6,0
							,7,0,0,0,5,0,1,8,9
							,0,0,0,0,0,0,9,5,0
							,0,0,0,0,4,8,0,3,1
							,0,0,0,0,0,6,0,7,8};
		//CANT SOLVE! (Method F[2]) http://www.sudokusolver.co.uk/solvemethods.html
		//Top left corner: http://puzzles.about.com/library/sudoku/blprsudokux01.htm
		int allNumbersC[] = {0,2,0,0,0,0,0,0,7
							,0,7,0,0,0,4,0,1,0
							,9,0,5,0,0,0,0,0,0
							,0,8,0,6,3,0,0,0,2
							,7,0,0,0,0,0,0,0,1
							,2,0,0,0,1,8,0,6,0
							,0,0,0,0,0,0,4,0,9
							,0,3,0,1,0,0,0,2,0
							,4,0,0,0,0,0,0,8,0};
		
		int template[] = 	{0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0
							,0,0,0,0,0,0,0,0,0};
		ArrayList<Integer> board[][] = new ArrayList[9][9];
		ArrayList<Integer> board1[][] = new ArrayList[9][9];
		ArrayList<Integer> board2[][] = new ArrayList[9][9];
		ArrayList<Integer> board3[][] = new ArrayList[9][9];
		ArrayList<Integer> board4[][] = new ArrayList[9][9];
		ArrayList<Integer> board5[][] = new ArrayList[9][9];
		ArrayList<Integer> board6[][] = new ArrayList[9][9];
		ArrayList<Integer> board7[][] = new ArrayList[9][9];
		ArrayList<Integer> board8[][] = new ArrayList[9][9];
		ArrayList<Integer> board9[][] = new ArrayList[9][9];
		ArrayList<Integer> board10[][] = new ArrayList[9][9];
		ArrayList<Integer> board11[][] = new ArrayList[9][9];
		ArrayList<Integer> board12[][] = new ArrayList[9][9];
		ArrayList<Integer> board13[][] = new ArrayList[9][9];
		ArrayList<Integer> board14[][] = new ArrayList[9][9];
		//ArrayList<Integer> board15[][] = new ArrayList[9][9];
		//ArrayList<Integer> board16[][] = new ArrayList[9][9];
		
		int z = 0;
		for (int y = 0; y < 9; y++)
			for (int x = 0; x < 9; x++) {
				ArrayList<Integer> boardList = new ArrayList<Integer>();
				ArrayList<Integer> boardList1 = new ArrayList<Integer>();
				ArrayList<Integer> boardList2 = new ArrayList<Integer>();
				ArrayList<Integer> boardList3 = new ArrayList<Integer>();
				ArrayList<Integer> boardList4 = new ArrayList<Integer>();
				ArrayList<Integer> boardList5 = new ArrayList<Integer>();
				ArrayList<Integer> boardList6 = new ArrayList<Integer>();
				ArrayList<Integer> boardList7 = new ArrayList<Integer>();
				ArrayList<Integer> boardList8 = new ArrayList<Integer>();
				ArrayList<Integer> boardList9 = new ArrayList<Integer>();
				ArrayList<Integer> boardList10 = new ArrayList<Integer>();
				ArrayList<Integer> boardList11 = new ArrayList<Integer>();
				ArrayList<Integer> boardList12 = new ArrayList<Integer>();
				ArrayList<Integer> boardList13 = new ArrayList<Integer>();
				ArrayList<Integer> boardList14 = new ArrayList<Integer>();
				//ArrayList<Integer> boardList15 = new ArrayList<Integer>();
				//ArrayList<Integer> boardList16 = new ArrayList<Integer>();
				
				board[y][x] = boardList;
				board1[y][x] = boardList1;
				board2[y][x] = boardList2;
				board3[y][x] = boardList3;
				board4[y][x] = boardList4;
				board5[y][x] = boardList5;
				board6[y][x] = boardList6;
				board7[y][x] = boardList7;
				board8[y][x] = boardList8;
				board9[y][x] = boardList9;
				board10[y][x] = boardList10;
				board11[y][x] = boardList11;
				board12[y][x] = boardList12;
				board13[y][x] = boardList13;
				board14[y][x] = boardList14;
				//board15[y][x] = boardList15;
				//board16[y][x] = boardList16;
				
				board[y][x].add(allNumbers[z]);
				board3[y][x].add(allNumbers2[z]);
				board4[y][x].add(allNumbers3[z]);
				board5[y][x].add(allNumbers4[z]);
				board6[y][x].add(allNumbers5[z]);
				board7[y][x].add(allNumbers6[z]);
				board8[y][x].add(allNumbers7[z]);
				board9[y][x].add(allNumbers8[z]);
				board10[y][x].add(allNumbers9[z]);
				board11[y][x].add(allNumbers0[z]);
				board12[y][x].add(allNumbersA[z]);
				board13[y][x].add(allNumbersB[z]);
				board14[y][x].add(allNumbersC[z]);
				//board15[y][x].add(allNumbersD[z]);
				//board16[y][x].add(allNumbersE[z]);
				board1[y][x].add(allNumbers1[z++]);
				board2[y][x].add(0);
		}
		
		SudokuBoard boards[] = {new SudokuBoard(board),new SudokuBoard(board1),new SudokuBoard(board2),
				new SudokuBoard(board3),new SudokuBoard(board4),new SudokuBoard(board5),new SudokuBoard(board6),
				new SudokuBoard(board7),new SudokuBoard(board8),new SudokuBoard(board9),new SudokuBoard(board10)
				,new SudokuBoard(board11),new SudokuBoard(board12),new SudokuBoard(board13),new SudokuBoard(board14)};
		return boards;
		
	}
}