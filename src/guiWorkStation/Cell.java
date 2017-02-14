package guiWorkStation;

import java.awt.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Cell {
	
	private JFormattedTextField cell;
	
	public Cell() {
		cell = null;
		try {
			cell = new JFormattedTextField(new MaskFormatter("#"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	public Cell(int initialValue) {
		//TODO Handle anomalies
		cell = null;
		try {
			cell = new JFormattedTextField(new MaskFormatter("#"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cell.setValue(initialValue);
	}
}
