package sudoku;

public class InValidNumberException extends SudokuException {

	private static final long serialVersionUID = 1L;
	
	public InValidNumberException() {
		super();
	}
	
	public InValidNumberException(String message) {
		super(message);
	}

}
