import java.util.ArrayList;

import sudoku.InValidGridException;


//3 public methods + 15 private methods + 1 constructor. 
@SuppressWarnings("unchecked")
public class SudokuDecipher extends SudokuBoard {
	
	private boolean boardAltered;
	private final int REPRESENT_COL = 0,REPRESENT_ROW = 1, REPRESENT_REGION = 2;
	private int difficultyLevel, attempts;
	
	public SudokuDecipher(ArrayList<Integer>[][] initialBoard) {
		super(initialBoard);
		boardAltered = false;
		difficultyLevel = 0;
		attempts = 0;
	}
	public SudokuDecipher() {
		super();
		boardAltered = false;
		difficultyLevel = 0;
		attempts = 0;
	}
	
	/**
	 * Core of SudokuSolver. It uses inflateBoard and a combination of trim(), findTheUnwonted(),
	 * 		ponderEnigma(), ponderMore(), and exploitChains().
	 * @return Number of cycles. Usually 1-2.
	 */
	public boolean compute() {
		this.inflateBoard();
		if (attempts > 2)
			return false;
		attempts++;
		int attempts = 0;
		while (++attempts < 3) {
			boardAltered = true;
			while (boardAltered) {
				boardAltered = false;
				this.trim();
			}
			if (isComplete())
				return true;
			boardAltered = true;
			while (boardAltered) {
				boardAltered = false;
				this.findTheUnwonted();
			}
			if (isComplete())
				return true;
			boardAltered = true;
			while (boardAltered) {
				boardAltered = false;
				this.ponderEnigma();
			}
			if (isComplete())
				return true;
			
			boardAltered = true;
			while (boardAltered) {
				boardAltered = false;
				this.ponderMore();
			}
		}
		boardAltered = true;
		while (boardAltered) {
			boardAltered = false;
			this.exploitChains();
		}
		if (isComplete())
			return true;
		else
			return compute();
	}
	
	/**
	 * 
	 * @return true if all cells are decisive and congruent.
	 */
	public boolean isComplete() {
		for (int k = 0; k < 9; k++)
			if (!isCompleteArray(this.getCellsInCol(k)) || !isCompleteArray(this.getCellsInRow(k)) || 
					!isCompleteArray(this.getCellsInRegion(k)))
				return false;
		return true;
	}
	private boolean isCompleteArray(ArrayList<Integer>[] array) {
		for (int k = 0; k < array.length; k++) {
			if (array[k].size() != 1 || array[k].get(0) == SudokuBoard.EMPTY)
				return false;
			for (int j = k+1; j < array.length; j++)
				if (array[j].size() == 1 && array[k].size() == 1 && array[j].get(0).intValue() == array[k].get(0).intValue()) {
					return false;
				}
		}
		return true;
	}
	
	/**
	 * 
	 * @return a boolean. true represents the board is valid, meaning no decisive number occurs twice
	 * 		in the same region, row, or column.
	 */
	public boolean isValid() {
		for (int k = 0; k < 9; k++)
			if (!isValidArray(this.getCellsInCol(k)) || !isValidArray(this.getCellsInRow(k)) || 
					!isValidArray(this.getCellsInRegion(k)))
				return false;
		return true;
	}
	private boolean isValidArray(ArrayList<Integer>[] array) {
		for (int k = 0; k < array.length; k++) {
			for (int j = k+1; j < array.length; j++)
				if (array[j].size() == 1 && array[k].size() == 1 && array[j].get(0).intValue() == array[k].get(0).intValue()) {
					return false;
				}
		}
		return true;
	}
	
	public int getDifficultyLevel() {
		return difficultyLevel-30;
	}

	/**
	 * Step 1: Populate every cell in the grid.
	 */
	public void inflateBoard() {
		//TODO Find and handle anomalies.
		ArrayList<Integer> col_n[] = new ArrayList[SudokuBoard.COLUMN];
		ArrayList<Integer> region_n[] = new ArrayList[(SudokuBoard.ROW / 3) * (SudokuBoard.COLUMN / 3)];
		for (int y = 0; y < SudokuBoard.ROW; y++) {
			ArrayList<Integer> row_n = this.getDecisiveNumbers(this.getCellsInRow(y));
			
			for (int x = 0; x < SudokuBoard.COLUMN; x++) {
				
				int currentRegion = this.getRegion(x, y);
				if (currentRegion != this.getPreviousRegion(x, y)) {
					if (region_n[currentRegion] == null)
						region_n[currentRegion] = this.getDecisiveNumbers(this.getCellsInRegion(currentRegion));
				}
				if (col_n[x] == null)
					col_n[x] = this.getDecisiveNumbers(this.getCellsInCol(x));
				
				//Superfluous to try and fill decisive cells with possible numbers. 
				if (getCell(x,y).size() == 1 && getCell(x,y).get(0) != SudokuBoard.EMPTY)
					continue;
				
				//Inflating starts here.
				for (int z = 1; z <= (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; z++)
					if (!region_n[currentRegion].contains(z) && !row_n.contains(z) && !col_n[x].contains(z)) {
						boardAltered = true;
						if (isCellEmpty(x,y)) {
							ArrayList<Integer> cell = new ArrayList<Integer>();
							cell.add(z);
							setCell(x,y,cell);
						}
						else
							appendToCell(x,y,z);
					}
				//Add to decisive lists if the cell becomes mono-value. 
				if (getCell(x,y).size() == 1) {
					row_n.add(getCell(x,y).get(0));
					col_n[x].add(getCell(x,y).get(0));
					region_n[currentRegion].add(getCell(x,y).get(0));
				}
			}
		}
	}
	
	/**
	 * Step 2: If decisive numbers N occur in a column, row, OR region CRR, remove N from
	 * 		cells relative to CRR (comprehensive).
	 */
	public void trim() {
		//TODO Find and handle anomalies; Attempt to make this method more efficient since it is
		//used frequently.
		ArrayList<Integer> col_n[] = new ArrayList[SudokuBoard.COLUMN];
		ArrayList<Integer> region_n[] = new ArrayList[(SudokuBoard.ROW / 3) * (SudokuBoard.COLUMN / 3)];
		for (int y = 0; y < SudokuBoard.ROW; y++) {
			ArrayList<Integer> row_n = this.getDecisiveNumbers(this.getCellsInRow(y));
			for (int x = 0; x < SudokuBoard.COLUMN; x++) {
				
				int currentRegion = this.getRegion(x, y);
				if (currentRegion != this.getPreviousRegion(x, y)) {
					if (region_n[currentRegion] == null)
						region_n[currentRegion] = this.getDecisiveNumbers(this.getCellsInRegion(currentRegion));
				}
				if (col_n[x] == null)
					col_n[x] = this.getDecisiveNumbers(this.getCellsInCol(x));
				
				//Superfluous to clean an empty or decisive cell. 
				if (getCell(x,y).size() == 1 || isCellEmpty(x,y))
					continue;
				//Cleaning starts here
				for (int z = 0; z < getCell(x,y).size(); z++) {
					int zValue = getCell(x,y).get(z);
					if (row_n.contains(zValue) || col_n[x].contains(zValue) || region_n[currentRegion].contains(zValue)) {
						this.removeFromCell(x, y, zValue);
						boardAltered = true;
						//Add to decisive lists if the cell becomes mono-value. 
						if (getCell(x,y).size() == 1) {
							difficultyLevel++;
							row_n.add(getCell(x,y).get(0));
							col_n[x].add(getCell(x,y).get(0));
							region_n[currentRegion].add(getCell(x,y).get(0));
						}
						
					}
				}
			}
		}
	}

	/**
	 * Step 3 (alternative #1): If number N occurs only in one cell C in a column, row, OR region,
	 * 		set C to N (comprehensive). 
	 */
	public void findTheUnwonted() {
		//TODO MAKE IT MORE EFFICIENT. 
		/*
		 * k = The region/cell/row number.
		 * c = values in the IndecisiveList (used as the unique number if one is added).
		 * q = Used as the index of the unique number in the array of ArrayList.
		 */
		for (int k = 0; k < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; k++) {
			int region[] = getIndecisiveNumbers(this.getCellsInRegion(k));
			int column[] = getIndecisiveNumbers(this.getCellsInCol(k));
			int row[] = getIndecisiveNumbers(this.getCellsInRow(k));
			//Iterate through the indecisiveNumbers
			for (int c = 0; c < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; c++) {
				if (region[c] == 1) {
					ArrayList<Integer> narrowNumber[] = this.getCellsInRegion(k);
					for (int q = 0; q < narrowNumber.length; q++)
						if (narrowNumber[q].contains(c+1)) {
							ArrayList<Integer> decisiveNumber = new ArrayList<Integer>();
							decisiveNumber.add(c+1);
							int compoundNum = this.findLocInRegion(k, q);
							this.setCell(compoundNum/10, compoundNum % 10, decisiveNumber);
							boardAltered = true;
							while (boardAltered) {
								boardAltered = false;
								trim();
							}
							boardAltered = true;
							difficultyLevel+= 4;
							narrowNumber = this.getCellsInRegion(k);
							region = getIndecisiveNumbers(narrowNumber);
							column = getIndecisiveNumbers(this.getCellsInCol(k));
							row = getIndecisiveNumbers(this.getCellsInRow(k));
						}
				}
				if (column[c] == 1) {
					ArrayList<Integer> narrowNumber[] = this.getCellsInCol(k);
					for (int q = 0; q < narrowNumber.length; q++)
						if (narrowNumber[q].contains(c+1)) {
							ArrayList<Integer> decisiveNumber = new ArrayList<Integer>();
							decisiveNumber.add(c+1);
							this.setCell(k, q, decisiveNumber);
							boardAltered = true;
							while (boardAltered) {
								boardAltered = false;
								trim();
							}
							boardAltered = true;
							difficultyLevel+= 4;
							narrowNumber = this.getCellsInCol(k);
							region = getIndecisiveNumbers(this.getCellsInRegion(k));
							column = getIndecisiveNumbers(narrowNumber);
							row = getIndecisiveNumbers(this.getCellsInRow(k));
						}
					
				}
				if (row[c] == 1) {
					ArrayList<Integer> narrowNumber[] = this.getCellsInRow(k);
					for (int q = 0; q < narrowNumber.length; q++)
						if (narrowNumber[q].contains(c+1)) {
							ArrayList<Integer> decisiveNumber = new ArrayList<Integer>();
							decisiveNumber.add(c+1);
							this.setCell(q,k,decisiveNumber);
							boardAltered = true;
							while (boardAltered) {
								boardAltered = false;
								trim();
							}
							boardAltered = true;
							difficultyLevel+= 4;
							narrowNumber = this.getCellsInRow(k);
							region = getIndecisiveNumbers(this.getCellsInRegion(k));
							column = getIndecisiveNumbers(this.getCellsInCol(k));
							row = getIndecisiveNumbers(narrowNumber);
						}
				}
			}
				
		}
	}

	/**
	 * Step 4 (alternative #2): If number N in a row or column RC all occur in region R, one can eliminate N from
	 * 		R excluding the cells that are communal with RC (comprehensive).
	 */
	public void ponderEnigma() {
		for (int k = 0; k < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; k++) {
			ArrayList<Integer> column[] = this.getCellsInCol(k), row[] = this.getCellsInRow(k), region[] = this.getCellsInRegion(k);
			String decisiveString_Col = this.getDecisiveString(column), decisiveString_Row = this.getDecisiveString(row),
			decisiveString_Region = this.getDecisiveString(region);
			//iterate through the 9 possible numbers of a sudoku.
			for (int c = 1; c <= (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; c++) {
				//If c is not in the string, then it is indecisive. If it is indecisive,
				//find all its location. If all the locations occur in the same region...
				if (!decisiveString_Col.contains(String.valueOf(c))) {
					ArrayList<Integer> locs = this.findNumXLocs(column, c, k, REPRESENT_COL);
					if (locs.size() <= 3) {
						if (locs.size() == 0)
							throw new InValidGridException();
						int currentRegion = this.getRegion(locs.get(0) / 10, locs.get(0) % 10);
						boolean inRegion = true;
						
						//Checks if c occurs in any region except int region. 
						for (int q = 1; q < locs.size(); q++)
							if (this.getRegion(locs.get(q) / 10, locs.get(q) % 10) != currentRegion) {
								inRegion = false;
								break;
							}
						ArrayList<Integer> regionArray[] = this.getCellsInRegion(currentRegion);
						//Checks for other occurrence of c in cells of the specific region excluding
						//the cells in the ArrayList<Integer> locs.
						int numbOfX = 0;
						for (int q = 0; q < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; q++) {
							if (locs.contains(this.findLocInRegion(currentRegion, q)))
								continue;
							if (regionArray[q].contains(c))
								numbOfX++;
						}
						if (inRegion && numbOfX > 0) {
							removeAnomalies(currentRegion,c,locs,REPRESENT_REGION);
							column = this.getCellsInCol(k); row = this.getCellsInRow(k); region = this.getCellsInRegion(k);
							decisiveString_Col = this.getDecisiveString(column); decisiveString_Row = this.getDecisiveString(row);
							decisiveString_Region = this.getDecisiveString(region);
						}
					}
				}
				if (!decisiveString_Row.contains(String.valueOf(c))) {
					ArrayList<Integer> locs = this.findNumXLocs(row, c, k, REPRESENT_ROW);
					if (locs.size() <= 3) {
						if (locs.size() == 0)
							throw new InValidGridException();
						int currentRegion = this.getRegion(locs.get(0) / 10, locs.get(0) % 10);
						boolean inRegion = true; 
						for (int q = 1; q < locs.size(); q++)
							if (this.getRegion(locs.get(q) / 10, locs.get(q) % 10) != currentRegion) {
								inRegion = false;
								break;
							}
						ArrayList<Integer> regionArray[] = this.getCellsInRegion(currentRegion);
						int numbOfX = 0;
						for (int q = 0; q < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; q++) {
							if (locs.contains(this.findLocInRegion(currentRegion, q)))
								continue;
							if (regionArray[q].contains(c))
								numbOfX++;
						}
						if (inRegion && numbOfX > 0) {
							removeAnomalies(currentRegion,c,locs, REPRESENT_REGION);
							column = this.getCellsInCol(k); row = this.getCellsInRow(k); region = this.getCellsInRegion(k);
							decisiveString_Col = this.getDecisiveString(column); decisiveString_Row = this.getDecisiveString(row);
							decisiveString_Region = this.getDecisiveString(region);
						}
					}
				}
				if (!decisiveString_Region.contains(String.valueOf(c))) {
					ArrayList<Integer> locs = this.findNumXLocs(region, c, k, REPRESENT_REGION);
					if (locs.size() <= 3) {
						if (locs.size() == 0)
							throw new InValidGridException();
						//Get the associated column/row relative to the locs.
						int RC = getRC(locs);
						if (RC == -1)
							continue;
						if (RC % 10 == 0) {
							ArrayList<Integer> RCArray[] = this.getCellsInCol(RC / 10);
							int numbOfX = 0;
							for (int q = 0; q < RCArray.length; q++) {
								if (locs.contains(RC / 10 * 10 + q))
									continue;
								if (RCArray[q].contains(c))
									numbOfX++;
							}
							if (numbOfX > 0) {
								removeAnomalies(RC / 10,c,locs, REPRESENT_COL);
								column = this.getCellsInCol(k); row = this.getCellsInRow(k); region = this.getCellsInRegion(k);
								decisiveString_Col = this.getDecisiveString(column); decisiveString_Row = this.getDecisiveString(row);
								decisiveString_Region = this.getDecisiveString(region);
							}
						}
						if (RC % 10 == 1) {
							ArrayList<Integer> RCArray[] = this.getCellsInRow(RC / 10);
							int numbOfX = 0;
							for (int q = 0; q < RCArray.length; q++) {
								if (locs.contains(q*10 + RC / 10))
									continue;
								if (RCArray[q].contains(c))
									numbOfX++;
							}
							if (numbOfX > 0) {
								removeAnomalies(RC / 10,c,locs, REPRESENT_ROW);
								column = this.getCellsInCol(k); row = this.getCellsInRow(k); region = this.getCellsInRegion(k);
								decisiveString_Col = this.getDecisiveString(column); decisiveString_Row = this.getDecisiveString(row);
								decisiveString_Region = this.getDecisiveString(region);
							}
						}

					}
				}
			}
		}
				
	}
	/**
	 * @see ponderEnigma()
	 * @param whichOne self-explanatory.
	 * @param x the number to remove.
	 * @param excluding the cells not to remove x from.
	 */
	private void removeAnomalies(int whichOne, int x, ArrayList<Integer> excluding, int representation) {
		if (representation == REPRESENT_COL) {
			ArrayList<Integer> array[] = this.getCellsInCol(whichOne);
			for (int k = 0; k < array.length; k++)
				if (array[k].contains(x) && !excluding.contains(whichOne*10 + k)) {
					this.removeFromCell(whichOne,k,x);
					difficultyLevel+= 8;
					boardAltered = true;
					while (boardAltered) {
						boardAltered = false;
						trim();
					}
				}
		}
		if (representation == REPRESENT_ROW) {
			ArrayList<Integer> array[] = this.getCellsInRow(whichOne);
			for (int k = 0; k < array.length; k++)
				if (array[k].contains(x) && !excluding.contains(k*10 + whichOne)) {
					this.removeFromCell(k,whichOne,x);
					boardAltered = true;
					difficultyLevel+= 8;
					while (boardAltered) {
						boardAltered = false;
						trim();
					}
				}
		}
		if (representation == REPRESENT_REGION) {
			ArrayList<Integer> array[] = this.getCellsInRegion(whichOne);
			for (int k = 0; k < array.length; k++)
				if (array[k].contains(x) && !excluding.contains(findLocInRegion(whichOne, k))) {
					this.removeFromCell(findLocInRegion(whichOne, k) / 10,findLocInRegion(whichOne, k) % 10,x);
					boardAltered = true;
					difficultyLevel+= 8;
					while (boardAltered) {
						boardAltered = false;
						trim();
					}
				}
		}
		

	}
	/**
	 * It takes an ArrayList of locs of number N in a region. If N occurs in the same row or column,
	 * 		it returns the coordinate of that row or column. Else it returns -1.
	 * @param locs an ArrayList with a size less than or equal to 3.
	 * @return First digit is the column/row number.
	 * 		   Second digit represents column or row or neither. REPRESENT_COL = 0,
	 * 				REPRESENT_ROW = 1, NEITHER = -1)
	 */
	private int getRC(ArrayList<Integer> locs) {
		int Xi = locs.get(0) / 10, Yi = locs.get(0) % 10;
		boolean isX = true, isY = true;
		for (int q = 1; q < locs.size(); q++) {
			if (locs.get(q) / 10 != Xi)
				isX = false;
			if (locs.get(q) % 10 != Yi)
				isY = false;
		}
		if (isX)
			return Xi * 10 + REPRESENT_COL;
		if (isY)
			return Yi * 10 + REPRESENT_ROW;
		return -1;
	}
	
	/**
	 * Step 5 (alternative #3): Comprehensiveness of getCongruentNumbs(ArrayList<Integer>[] array, int whichOne,
	 * 		int representation) (comprehensive).
	 * @see getCongruentNumbs()
	 */
	public void ponderMore() {
		//Iterate the board
		for (int k = 0; k < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; k++) {
			ArrayList<Integer> column[] = this.getCellsInCol(k), row[] = this.getCellsInRow(k), 
				region[] = this.getCellsInRegion(k);
			ArrayList<ArrayList<Integer>> congruentCol[] = this.getCongruentNumbs(column, k, 0),
				congruentRow[] = this.getCongruentNumbs(row, k, 1), congruentRegion[] = this.getCongruentNumbs(region, k, 2);
			boardAltered = false;
			if (congruentCol[0].size() > 0)
				this.setCongruentNumbs(congruentCol);
			if (congruentRow[0].size() > 0)
				this.setCongruentNumbs(congruentRow);
			if (congruentRegion[0].size() > 0)
				this.setCongruentNumbs(congruentRegion);
			if (boardAltered) {
				while (boardAltered) {
					boardAltered = false;
					trim();
				}
			}
		}
	}
	/**
	 * Precondition: numbs has a length of 2. Both elements have a size greater than 0.
	 * Literally wasted like 2 hours on this method because of aliases. Thank god for the clone method!
	 * @param numbs 
	 */
	private void setCongruentNumbs(ArrayList<ArrayList<Integer>>[] numbs) {
		for (int k = 0; k < numbs[0].size(); k++) {
			for (int c = 0; c < numbs[0].get(k).size(); c++) {
				if (this.getCell(numbs[1].get(k).get(c) / 10, numbs[1].get(k).get(c) % 10).size() <= numbs[0].get(k).size())
					continue;
				ArrayList<Integer> newCell = (ArrayList<Integer>) numbs[0].get(k).clone();
				this.setCell(numbs[1].get(k).get(c) / 10, numbs[1].get(k).get(c) % 10, newCell);
				boardAltered = true;
				difficultyLevel+= 16;
			}
		}
	}
	/**
	 * Core of ponderMore()
	 * @param array column, row, or region.
	 * @param whichOne whichOne indicate what region, column, or row it is.
	 * @param representation identification of row or cell. (REPRESENT_COL = 0,
	 * 		REPRESENT_ROW = 1)
	 * @return an array of ArrayList<ArrayList<Integer>> with length = 2. The first ArrayList<ArrayList<Integer>>
	 * 		is filled with numbers that occur in the same cells. The second ArrayList<ArrayList<Integer>> is
	 * 		filled with the locations of the cells. 
	 * Example: Assume an ArrayList<Integer> row[] = [4578, 2347, 1, 6, 9, 458, 24, 237, 37]. Notice the numbers
	 * 		5 and 8. They both occur in the same cell and no where else. The first element in the return array
	 * 		would contain [[5,8]] and the second element would contain [[LOCATION OF CELL 0 IN BOARD, LOCATION OF 
	 * 		CELL 5 IN BOARD]].
	 */
	private ArrayList<ArrayList<Integer>>[] getCongruentNumbs(ArrayList<Integer>[] array, int whichOne,
			int representation) {
		//Triplets have not been tested on since it has not occurred yet.
		ArrayList<ArrayList<Integer>> congruentNumbs[] = new ArrayList[2];
		ArrayList<Integer> invalidPairs = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> supCongruentNumbs = new ArrayList<ArrayList<Integer>>(),
			supCongruentNumbsLocs = new ArrayList<ArrayList<Integer>>();
		//Iterate through the array.
		for (int k = 0; k < array.length; k++) {
			if (array[k].size() == 1)
				continue;
			//Iterate through the ArrayList<Integer>/array[k]
			for (int c = 0; c < array[k].size(); c++) {
				int congruence = 0, congruentLoc = 0;
				//Compare c to the rest of the ArrayList<Integer>/array[k]
				others:
				for (int ci = c+1; ci < array[k].size(); ci++) {
					if (invalidPairs.contains(array[k].get(c)*10 + array[k].get(ci))) {
						congruence = 0;
						continue others;
					}
					//Iterating through the array again to see if a pair occurs in the array.
					for (int q = 0; q < array.length; q++) {
						if ((array[q].contains(array[k].get(c)) && !array[q].contains(array[k].get(ci))) ||
								(!array[q].contains(array[k].get(c)) && array[q].contains(array[k].get(ci)))) {
							congruence = 0;
							congruentLoc = 0;
							invalidPairs.add(array[k].get(c) * 10 + array[k].get(ci));
							continue others;
						}

						else if (array[q].contains(array[k].get(c)) && array[q].contains(array[k].get(ci))) {
							congruentLoc = q;
							congruence++;
						}

					}
					int congruencept2 = 0;
					ArrayList<Integer> equivicalNumsLocs = new ArrayList<Integer>(),
					equivicalNums = new ArrayList<Integer>();
					if (congruence == 2) {
						equivicalNums.add(array[k].get(c));
						equivicalNums.add(array[k].get(ci));
						if (representation == REPRESENT_COL) {
							equivicalNumsLocs.add(whichOne*10 + k);
							equivicalNumsLocs.add(whichOne*10 + congruentLoc);
						}
						if (representation == REPRESENT_ROW) {
							equivicalNumsLocs.add(k*10 + whichOne);
							equivicalNumsLocs.add(congruentLoc*10 + whichOne);
						}
						if (representation == REPRESENT_REGION) {
							equivicalNumsLocs.add(this.findLocInRegion(whichOne,k));
							equivicalNumsLocs.add(this.findLocInRegion(whichOne,congruentLoc));
						}
					}
					if (congruence > 2) {
						//If for w/e reason there are more occurrence of the triplets+ than the
						//size of the cell, stop.
						if (array[k].size() < congruence || array[congruentLoc].size() < congruence) {
							congruence = 0;
							congruentLoc = 0;
							invalidPairs.add(array[k].get(c) * 10 + array[k].get(ci));
							continue others;
						}
						equivicalNums.add(array[k].get(c));
						equivicalNums.add(array[k].get(ci));
						if (representation == REPRESENT_COL) {
							equivicalNumsLocs.add(whichOne*10 + k);
							equivicalNumsLocs.add(whichOne*10 + congruentLoc);
						}
						if (representation == REPRESENT_ROW) {
							equivicalNumsLocs.add(k*10 + whichOne);
							equivicalNumsLocs.add(congruentLoc*10 + whichOne);
						}
						if (representation == REPRESENT_REGION) {
							equivicalNumsLocs.add(this.findLocInRegion(whichOne,k));
							equivicalNumsLocs.add(this.findLocInRegion(whichOne,congruentLoc));
						}
						//Find loc of the other cell(s). The other loc has to be in between c and q.
						for (int x = c+1; x < congruentLoc; x++) {
							if (array[x].contains(array[k].get(c)) && array[x].contains(array[k].get(ci))) {
								if (representation == REPRESENT_COL)
									equivicalNumsLocs.add(whichOne*10 + x);
								if (representation == REPRESENT_ROW)
									equivicalNumsLocs.add(x*10 + whichOne);
								if (representation == REPRESENT_REGION)
									equivicalNumsLocs.add(this.findLocInRegion(whichOne,x));
							}

						}
						//Find the numbers that are congruent.
						//Find the other congruence-2 number(s).
						ArrayList<Integer> cell1 = this.getCell(equivicalNumsLocs.get(0) / 10, equivicalNumsLocs.get(0) % 10);
						//Iterate through the location's cell.
						for (int y = 0; y < cell1.size(); y++) {
							//If it's the known values, skip em.
							if (cell1.get(y).intValue() != array[k].get(c).intValue() && 
									cell1.get(y).intValue() != array[k].get(ci).intValue()) {
								boolean numbIsValid = true;
								//compare cell to the other location's cell in equivicalNumsLocs.
								for (int x = 1; x < equivicalNumsLocs.size(); x++) {
									ArrayList<Integer> cell2 = this.getCell(equivicalNumsLocs.get(x) / 10, equivicalNumsLocs.get(x) % 10);
									if (!cell2.contains(y))
										numbIsValid = false;
								}
								if (numbIsValid) {
									congruencept2++;
									equivicalNums.add(cell1.get(y));
								}
							}
						}
					}
					if (congruencept2+2 == congruence && equivicalNums.size() == equivicalNumsLocs.size()) {
						supCongruentNumbs.add(equivicalNums);
						supCongruentNumbsLocs.add(equivicalNumsLocs);
						for (int x = 0; x < equivicalNums.size(); x++)
							for (int y = 1; y < equivicalNums.size(); y++)
								invalidPairs.add(equivicalNums.get(x) * 10 + equivicalNums.get(y));
					}
					else {
						congruence = 0;
						congruentLoc = 0;
						invalidPairs.add(array[k].get(c) * 10 + array[k].get(ci));
						continue others;
					}
				}					
			}
		}
		congruentNumbs[0] = supCongruentNumbs;
		congruentNumbs[1] = supCongruentNumbsLocs;
		return congruentNumbs;
	}
	
	/**
	 * Find a group of N cells which contain only N indecisive numbers where 2 <= N <=4. If such a "chain"
	 * 		exists, we can eliminate N indecisive numbers from the other cells. 
	 * Example: Assume row R = [378, 38, 6789, 37, 458, 2, 34568, 489, 1]. R[0], R[1], and R[3] only contain
	 * 		3, 7, or 8; thus, 3, 7, and 8 must go in those cells; consequently, we can eliminate 78 from
	 * 		R[2], 8 from R[4], 38 from R[6], and 8 from R[7]. R now equals: [378, 38, 69, 37, 45, 2, 456, 49, 1]
	 * 
	 * Example 2: Assume column C = [56, 348, 2358, 34, 26, 9, 47, 1, 37]. C[3], C[6], & C[8] only contain
	 * 		3, 4, & 7; thus, 3, 4, & 7 must go in those cells; consequently, we can eliminate 34 from C[1] and 3
	 * 		from C[2]. C now equals: [56, 8, 258, 34, 26, 9, 47, 1, 37].
	 */
	public void exploitChains() {
		for (int k = 0; k < (SudokuBoard.ROW + SudokuBoard.COLUMN) / 2; k++) {
			ArrayList<Integer>[] column = this.getCellsInCol(k), row = this.getCellsInRow(k),
				region = this.getCellsInRegion(k);
			if (exploitChainsInArray(column,k,REPRESENT_COL))
				trim();
			if (exploitChainsInArray(row,k,REPRESENT_ROW))
				trim();
			if (exploitChainsInArray(region,k,REPRESENT_REGION))
				trim();
		}
	}

	
	/**
	 * @see exploitChains()
	 * @param array column, row, or region.
	 * @param whichOne location of the column, row, region.
	 * @param representation identification of row or cell. (REPRESENT_COL = 0,
	 * 		REPRESENT_ROW = 1)
	 * @return True if array is changed.
	 */
	public boolean exploitChainsInArray(ArrayList<Integer>[] array, int whichOne, int representation) {
		//System.out.println("Initializing exploitChainsInArray...");
		boolean arrayAdjusted = false;
		for (int n = 2; n <= 4; n++) {
			if (arrayAdjusted)
				if (representation == REPRESENT_COL)
					array = this.getCellsInCol(whichOne);
				else if (representation == REPRESENT_ROW)
					array = this.getCellsInRow(whichOne);
				else if (representation == REPRESENT_REGION)
					array = this.getCellsInRegion(whichOne);
			//System.out.printf("n = %d%n",n);
			ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
			for (int k = 0; k < array.length; k++)
				if (array[k].size() <= n && array[k].size() >= 2)
					cells.add(array[k]);
			//System.out.printf("\tcells = %s%n",cells);
			
			if (cells.size() == n) {
				ArrayList<Integer> chainedNumbers = new ArrayList<Integer>();
				for (int k = 0; k < cells.size(); k++)
					for (int c = 0; c < cells.get(k).size(); c++)
						if (!chainedNumbers.contains(cells.get(k).get(c)))
							chainedNumbers.add(cells.get(k).get(c));
				if (chainedNumbers.size() == n) {
					arrayAdjusted = this.exploitChainsInCells(array, chainedNumbers, cells, whichOne, representation,n);

				}

			}
			if (cells.size() > n) {
				//System.out.printf("\tCells size is greater than n (%d > %d)%n", cells.size(), n);
					if (n == 2) {
						other:
						for (int k = 0; k < cells.size(); k++)
							for (int ki = k+1; ki < cells.size(); ki++)
								if (cells.get(k).equals(cells.get(ki))) {
									ArrayList<ArrayList<Integer>> excludingCells = new ArrayList<ArrayList<Integer>>();
									ArrayList<Integer> chainedNumbers = new ArrayList<Integer>();
									excludingCells.add(cells.get(k));
									excludingCells.add(cells.get(ki));
									chainedNumbers.add(cells.get(k).get(0));
									chainedNumbers.add(cells.get(k).get(1));
									if (k > ki) {
										cells.remove(k);
										cells.remove(ki);
									}
									else if (ki > k) {
										cells.remove(ki);
										cells.remove(k);
									}
									k--;
									arrayAdjusted = this.exploitChainsInCells(array, chainedNumbers, excludingCells, whichOne, representation,n);
									//Remove chainedNumbers from cells.
									for (int c = 0; c < cells.size(); c++) {
										if (!excludingCells.contains(cells.get(c)))
											for (int ci = 0; ci < cells.get(c).size(); ci++)
												if (chainedNumbers.contains(cells.get(c).get(ci))) {
													cells.get(c).remove(cells.get(c).indexOf(cells.get(c).get(ci)));
													ci--;
												}
									}

									//Remove decisive numbers.
									for (int q = 0; q < cells.size(); q++)
										if (cells.get(q).size() < 2) {
											cells.remove(q);
											q--;
										}
									continue other;
								}
					}
					if (n > 2) {
						//System.out.printf("\t\t@ n > 2 phase. n = %d%n",n);
						for (int k = 0; k < cells.size(); k++) {
							ArrayList<Integer> chainedNumbers = new ArrayList<Integer>();
							ArrayList<ArrayList<Integer>> chainedCells = new ArrayList<ArrayList<Integer>>(), 
								alreadyChecked = new ArrayList<ArrayList<Integer>>();
							if (alreadyChecked.contains(cells.get(k)))
								continue;

							//System.out.printf("\t\tWill be using %s from %s to compare to th rest of the array.%n",cells.get(k),cells);
														
							if (cells.get(k).size() == n && chainedCells.size() == 0) {
								chainedCells.add(cells.get(k));
								for (int c = 0; c < cells.get(k).size(); c++)
									chainedNumbers.add(cells.get(k).get(c));
								alreadyChecked.add(cells.get(k));
								ArrayList<Integer> compareToThis = cells.get(k);
								for (int ki = 0; ki < cells.size(); ki++) {
									if (cells.get(ki) == compareToThis)
										continue;

									boolean isValid = true;
									for (int kii = 0; kii < cells.get(ki).size(); kii++)
										if (!chainedNumbers.contains(cells.get(ki).get(kii))) {
											isValid = false;
											break;
										}
									if (isValid) {
										chainedCells.add(cells.get(ki));
										cells.remove(ki);
										ki--;
									}
									
								}
								if (chainedCells.size() == n) {
									arrayAdjusted = this.exploitChainsInCells(array, chainedNumbers, chainedCells, whichOne, representation,n);
									//Remove the element we used to compare the list to.
									int comparedToLoc = cells.indexOf(chainedCells.get(0));
									if (comparedToLoc != -1)
										cells.remove(comparedToLoc);
									//Remove chainedNumbers from cells.
									for (int c = 0; c < cells.size(); c++) {
										if (!chainedCells.contains(cells.get(c)))
											for (int ci = 0; ci < cells.get(c).size(); ci++)
												if (chainedNumbers.contains(cells.get(c).get(ci))) {
													cells.get(c).remove(cells.get(c).indexOf(cells.get(c).get(ci)));
													ci--;
												}
									}

									//Remove decisive numbers.
									for (int q = 0; q < cells.size(); q++)
										if (cells.get(q).size() < 2) {
											cells.remove(q);
											q--;
										}
								}
								
								else if (chainedCells.size() != n) {
									//Add the elements back to chainedCells excluding the first one.
									for (int q = 1; q < chainedCells.size(); q++) {
										cells.add(chainedCells.get(q));
										chainedCells.remove(q);
										q--;
									}
									chainedCells = new ArrayList<ArrayList<Integer>>();
									chainedNumbers = new ArrayList<Integer>();
									continue;
								}
							}
							if (cells.get(k).size() < n && chainedCells.size() == 0) {
								chainedCells.add(cells.get(k));
								for (int c = 0; c < cells.get(k).size(); c++)
									chainedNumbers.add(cells.get(k).get(c));
								ArrayList<ArrayList<Integer>> alreadyCheckedNested = new ArrayList<ArrayList<Integer>>();
								alreadyChecked.add(cells.get(k));
								ArrayList<Integer> compareToThis = cells.get(k);
								for (int ki = 0; ki < cells.size(); ki++) {
									if (cells.get(ki) == compareToThis) 
										continue;
									if (cells.get(ki).size() == n) {
										//check if all chainedNumbers are in it. 
										boolean isValid = true;
										for (int c = 0; c < chainedNumbers.size(); c++)
											if (!cells.get(ki).contains(chainedNumbers.get(c))) {
												isValid = false;
												break;
											}
										//System.out.printf("\t\tThe next element (%s) has a size equivalent to n (n = %d). Is it valid? %b%n",cells.get(ki),n,isValid);
										if (!isValid)
											continue;
										if (isValid) {
											//add the new element to the chainedNumbers.
											for (int c = 0; c < cells.get(ki).size(); c++)
												if (!chainedNumbers.contains(cells.get(ki).get(c)))
													chainedNumbers.add(cells.get(ki).get(c));
											//add the cell to chainedCells.
											chainedCells.add(cells.get(ki));
											//remove the cell from cells. 
											cells.remove(ki);
											//decrement ki
											ki--;
											continue;
										}
									}
									else if (cells.get(ki).size() < n) {
										//System.out.printf("\t\tAt element %s. ki = %d%n",cells.get(ki), ki);
										//HARD
										//check if at least one number from chainedNumbers appears only if
										//chainedNumbers.size() < n
										//in cells.get(ki).
										if (chainedNumbers.size() == n) {
											//System.out.println("\t\t\tchainedCells is full so we no longer need to check if only one possible number from chainedNumber exists.");
											boolean isValid = true;
											for (int c = 0; c < cells.get(ki).size(); c++)
												if (!chainedNumbers.contains(cells.get(ki).get(c))) {
													isValid = false;
													break;
												}
											if (isValid) {
												chainedCells.add(cells.get(ki));
												cells.remove(ki);
												ki--;
											}
											continue;
										}
										boolean occursOnce = false;
										for (int c = 0; c < cells.get(ki).size(); c++)
											if (chainedNumbers.contains(cells.get(ki).get(c)))
												occursOnce = true;
										if (occursOnce) {
											//System.out.printf("\t\tAt least 1 number from %s is in %s%n",chainedNumbers,cells.get(ki));
											//Add the numbers to chainedNumbers.
											for (int c = 0; c < cells.get(ki).size(); c++)
												if (!chainedNumbers.contains(cells.get(ki).get(c)))
													chainedNumbers.add(cells.get(ki).get(c));
											chainedCells.add(cells.get(ki));
											cells.remove(ki);
											ki--;
										}
										if (!occursOnce) {
											//System.out.printf("\t\tNo number from %s is in %s%n",chainedNumbers,cells.get(ki));
											if (!alreadyCheckedNested.contains(cells.get(ki))) {
												//Something like this could occur: 
												//[14,23,12,34]. The numbers are chained when n=4, but
												//cells.get(1) has no numbers from cells.get(0). So, we move
												//cells.get(1) to the end.
												ArrayList<Integer> temp = cells.get(ki);
												//System.out.printf("\t\t\tMoving %s to the back of the list%n",temp);
												cells.remove(ki);
												cells.add(temp);
												alreadyCheckedNested.add(temp);
												ki--;
												continue;
											}
											continue;
										}
									}
								}
								if (chainedCells.size() == n && chainedNumbers.size() == n) {
									arrayAdjusted = this.exploitChainsInCells(array, chainedNumbers, chainedCells, whichOne, representation,n);
									int comparedToLoc = cells.indexOf(chainedCells.get(0));
									if (comparedToLoc != -1)
										cells.remove(comparedToLoc);
									//Remove chainedNumbers from cells.
									for (int c = 0; c < cells.size(); c++) {
										if (!chainedCells.contains(cells.get(c)))
											for (int ci = 0; ci < cells.get(c).size(); ci++)
												if (chainedNumbers.contains(cells.get(c).get(ci))) {
												cells.get(c).remove(cells.get(c).indexOf(cells.get(c).get(ci)));
												ci--;
												}
									}

									//Remove decisive numbers.
									for (int q = 0; q < cells.size(); q++)
										if (cells.get(q).size() < 2) {
											cells.remove(q);
											q--;
										}

	
								}
								else {
									//Add the elements back to chainedCells excluding the first one.
									for (int q = 1; q < chainedCells.size(); q++) {
										cells.add(chainedCells.get(q));
										chainedCells.remove(q);
										q--;
									}
									chainedCells = new ArrayList<ArrayList<Integer>>();
									chainedNumbers = new ArrayList<Integer>();
									continue;
								}
							}
						}
					}
			}
		}
		return arrayAdjusted;
	}
	/**
	 * @see exploitChainsInArray().
	 * @param array column, row, or region.
	 * @param chainedNumbers Numbers to remove from array.
	 * @param excludingCells Don't remove chainedNumbers from cells listed in this ArrayList<ArrayList<Integer>>
	 * @param whichOne whichOne whichOne indicate what region, column, or row it is.
	 * @param representation identification of row or cell. (REPRESENT_COL = 0,
	 * 		REPRESENT_ROW = 1)
	 * @param indexOfComplexity used to equate difficulty score. Possible indexOfComplexity: 2, 3, 4.
	 * Removes all numbers in chainedNumbers in array excluding the cells in excludingCells. 
	 */
	public boolean exploitChainsInCells(ArrayList<Integer>[] array, ArrayList<Integer> chainedNumbers,
			ArrayList<ArrayList<Integer>> excludingCells, int whichOne, int representation, int indexOfComplexity) {
		boolean cellAdjusted = false;
		//System.out.printf("CLEANING UP THE NUMBERS %s FROM ARRAY %d (representation = %d).%n", chainedNumbers,whichOne,representation);
		for (int k = 0; k < array.length; k++)
			if (array[k].size() > 1 && !excludingCells.contains(array[k]))
				for (int c = 0; c < chainedNumbers.size(); c++) {
					if (representation == REPRESENT_COL)
						if (this.removeFromCell(whichOne,k,chainedNumbers.get(c)))
							cellAdjusted = true;
					if (representation == REPRESENT_ROW)
						if (this.removeFromCell(k,whichOne,chainedNumbers.get(c)))
							cellAdjusted = true;
					if (representation == REPRESENT_REGION)
						if (this.removeFromCell(this.findLocInRegion(whichOne,k)/10,this.findLocInRegion(whichOne,k)%10,chainedNumbers.get(c)))
							cellAdjusted = true;
				}
		
		if (cellAdjusted) {
			boardAltered = true;
			difficultyLevel+= (16*indexOfComplexity);
		}

		return cellAdjusted;
	}
		
	/**
	 * 
	 * @param array A region, column, or row.
	 * @return An int array. Index: Sudoku Number; consequently, the array has 9 elements.
	 * 		Value: The number of occurrence of each index (Sudoku Number). 
	 */
	private int[] getIndecisiveNumbers(ArrayList<Integer>[] array) {
		int indecisiveList[] = new int[9];
		for (int k = 0; k < array.length; k++)
			for (int c = 0; c < array[k].size(); c++) {
				if (array[k].size() == 1)
					continue;
				indecisiveList[array[k].get(c)-1]++;
			}
		return indecisiveList;
	}
	/**
	 * Used in ponderEnigma()
	 * @param array row, column, or region.
	 * @return A String of all the decisive numbers.
	 */
	private String getDecisiveString(ArrayList<Integer>[] array) {
		String decisiveString = "";
		for (int k = 0; k < array.length; k++)
			if (array[k].size() == 1)
				decisiveString+= String.valueOf(array[k].get(0));
		return decisiveString;
	}
	/**
	 * 
	 * @param array region, row, or column
	 * @param x 1 <= x <= 9. A number to look for in the array.
	 * @param whichOne location of the region, row, or column
	 * @param representation identification of row or cell. (REPRESENT_COL = 0,
	 * 		REPRESENT_ROW = 1)
	 * @return All the locations of x in the array. All integers are 1 or 2 digits. If
	 * 		2 digits, the first number represents the x-coordinate and the second one represents
	 * 		the y-coordinate. If one digit, x-coordinate is 0 and y-coordinate is the digit.
	 */
	private ArrayList<Integer> findNumXLocs(ArrayList<Integer>[] array, int x, int whichOne, int representation) {
		ArrayList<Integer> locs = new ArrayList<Integer>();
		if (representation == REPRESENT_COL)
			for (int k = 0; k < array.length; k++)
				if (array[k].contains(x))
					locs.add(whichOne*10 + k);
		if (representation == REPRESENT_ROW)
			for (int k = 0; k < array.length; k++)
				if (array[k].contains(x))
					locs.add(k*10 + whichOne);
		if (representation == REPRESENT_REGION)
			for (int k = 0; k < array.length; k++)
				if (array[k].contains(x))
					locs.add(this.findLocInRegion(whichOne, k));
		return locs;
			
	}
	
	private int getPreviousRegion(int col, int row) {
		if ((col >= 9 || col < 0) || (row >= 9 || row < 0))
			throw new OutOfGridException("ROW = " + row + "  COL = " + col);
		int px = col, py = row;
		if (col - 1 == -1) {
			px = SudokuBoard.COLUMN - 1;
			py = row-1;
		}
		return ((px-1)/3 + py/3*3);
	}
	
}
