package sudoku;

public class OutOfGridException extends InValidGridException {

	private static final long serialVersionUID = 1L;
	
	public OutOfGridException() {
		super();
	}
	
	public OutOfGridException(String message) {
		super(message);
	}

}
