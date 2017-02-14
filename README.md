#Sudoku Puzzle Solver
Sudoku Puzzle Solver is an app that I made in Java back in AP Computer Science (2010) that attempts to solve Sudoku Puzzles and rates them on difficulty. It solves the puzzle using constraints.

##A Constraint Satisfaction Problem
A Sudoku puzzle is defined as a logic-based, number-placement puzzle. The objective is to fill a 9×9 grid with digits in such a way that each column, each row, and each of the nine 3×3 grids that make up the larger 9×9 grid contains all of the digits from 1 to 9, called a **region**. Each Sudoku puzzle begins with some cells filled in. The player uses these seed numbers as a launching point toward finding the unique solution. It is important to stress the fact that no number from 1 to 9 can be repeated in any row or column (although, the can be repeated along the diagonals). 

There are many techniques to solve a Sudoku puzzle, all of which can be considered inferences and constraints on the state (i.e., a Sudoku board). Each cell has a list of possible values that can be placed in it. The first inference is the *trim()* method, which works very similar to the global constraints AllDiff. *trim()* modifies the Sudoku board such that cells that have only 1 option are eliminated from cells in the same row, column, or region. 

The next inference procedure is called *findTheUnwonted()*. It modifies the state of the board such that If number N occurs only once in cell C_i’s list of possible values in a column, region, or row, then reduce the cell’s possible values to just N.

The next inference procedure is called *ponderEngima()*. To understand this, consider the figure below:
![](/readme-pics/ponderEngimaExplanation.png)

Then suppose that you figure out where the number 2 occurs in the first row, and that it’s only possible in A, B or C. Then, you can conclude that it won’t occur in C_4 through C_9! Thus, you can eliminate that number from their list of possible values. 

The next inference procedure is called *ponderMore()*.Suppose some row X is [4578, 2347, 1, 6, 9, 458, 24, 237, 37]. Notice how “58” appears only in the first and sixth column of that row. Thus, we can reduce the column to: [4578, 2347, 1, 6, 9, 458, 24, 237, 37]

The last inference procedure is called *exploitChains()*. Find a group of N cells which contain only N indecisive numbers where 2 <= N <=4. If such a "chain"	exists, we can eliminate those (the N indecisive numbers) numbers from the other cells. Example: Assume row R = [378, 38, 6789, 37, 458, 2, 34568, 489, 1]. R[1], R[2], and R[4] only contain 3, 7, or 8; thus, 3, 7, and 8 must go in those cells; consequently, we can eliminate 78 from R[2], 8 from R[4], 38 from R[6], and 8 from R[7]. R now equals: [378, 38, 69, 37, 45, 2, 456, 49, 1]. 
Another example: assume column C = [56, 348, 2358, 34, 26, 9, 47, 1, 37]. C[3], C[6], & C[8] only contain 3, 4, & 7; thus, 3, 4, & 7 must go in those cells; consequently, we can eliminate 34 from C[1] and 3 from C[2]. C now equals: [56, 8, 258, 34, 26, 9, 47, 1, 37].

##GUI for Sudoku Board
![](/readme-pics/initial.png)

Figure 1: Initial board of the GUI

![](/readme-pics/evil-puzzle-initial.png)

Figure 2: Input (Known as one of the most ‘Evil’ Puzzles)

![](/readme-pics/evil-puzzle-solved.png)

Figure 3: The most evil puzzle deciphered!

##Run
Open your favorite Java IDE and simply run the DriverProgram.java and the UI should pop up. The code is thoroughly commented. Hope you like it as much as I had fun developing it!
