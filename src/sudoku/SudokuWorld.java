package sudoku;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
/**
 * 
 * @author Aziz
 * @version 5/29/2010: Created
 * 			8/5/2010: Properties was added.
 * 			8/6/2010: Get last opened time added.
 *
 */
public class SudokuWorld {
	
	private SudokuFrame sudoku;
	private java.util.Properties applicationProps;
	
	public SudokuWorld() {
		sudoku = new SudokuFrame();
		sudoku.addWindowListener(new TerminalOperation());
		
		try {
			loadProperties();
			sudoku.getSudokuGrid().setGrid(applicationProps.getProperty("board"));
			
			if (!sudoku.getSudokuGrid().isEmpty()) {
				String[] message = getTimeOfLastExecution();
				sudoku.displayText(message[0],1);
				sudoku.displayText(message[1],2);
			}
		} catch(FileNotFoundException e) { 
			System.err.println("Unable to read because the file \"appProperties\" " +
					"was not found."); 
		} catch (NullPointerException e1) {
			System.err.println("Unable to read because the file \"appProperties\" " +
			"was not found.");
		}
		catch (Exception e2) { e2.printStackTrace(); }
	}
	
	public void display() {
		sudoku.pack();
		sudoku.setLocationRelativeTo(null);
		sudoku.setVisible(true);
	}
	
	private void loadProperties() throws Exception {
		applicationProps = new java.util.Properties();
		FileReader in = null;
		System.out.println();
		
		try {
			in = new FileReader(new File(this.getClass()
					.getResource("appProperties").toURI()));
			applicationProps.load(in);

		} 
		finally {
			if (in != null)
				in.close();
		}
	}
	
	private String[] getTimeOfLastExecution() throws Exception {
		String[] lines = new String[2];
		java.io.BufferedReader in = null;
		java.text.SimpleDateFormat format = 
			new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		try {
			in = new java.io.BufferedReader(new FileReader(new File(this.getClass()
					.getResource("appProperties").toURI())));
			String content = "";
			while ((content = in.readLine()) != null)
				if (content.charAt(0) == '#' && content.length() > 13) {
					java.util.Calendar time = java.util.Calendar.getInstance();
					time.setTime(format.parse(content.substring(1)));
					lines[0] = String.format("Last used on %tb %1$td, %1$tY", time);
					lines[1] = String.format("      at %tl:%1$tM%1$tp", time);
					break;
				}
		}
		finally { 
			if (in != null)
				in.close();
		}
		return lines;
	}
	
	private class TerminalOperation extends java.awt.event.WindowAdapter {
		@Override
		public void windowClosing(java.awt.event.WindowEvent e) {
			java.io.FileWriter out = null;
			applicationProps.setProperty("board", sudoku.getSudokuGrid()
					.generatePropertyValue());
			try {
				out = new java.io.FileWriter(new File(this.getClass()
						.getResource("appProperties").toURI()));
				applicationProps.store(out, "Board Setup");
			} catch (FileNotFoundException e1) { 
				System.err.println("Unable to write because the file \"appProperties\"" +
				" was not found."); 
			} catch (NullPointerException e2) {
				System.err.println("Unable to write because the file \"appProperties\"" +
				" was not found."); 
			}
			catch (Exception e3) { e3.printStackTrace(); }
		}
	}
}
