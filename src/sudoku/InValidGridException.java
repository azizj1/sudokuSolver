package sudoku;

public class InValidGridException extends SudokuException {

	private static final long serialVersionUID = 1L;
	
	public InValidGridException() {
		super();
	}
	
	public InValidGridException(String message) {
		super(message);
	}

}
