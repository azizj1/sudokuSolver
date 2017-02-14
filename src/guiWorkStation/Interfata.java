package guiWorkStation;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfata extends JFrame {
  private static final int CELL_POINTS = 20;
  private static final int SIDE = (int)((double)CELL_POINTS * 5 / 2);
  private static final Dimension CELL_SIZE = new Dimension(SIDE, SIDE);
  private static final Font CELL_FONT = new Font(Font.DIALOG, Font.BOLD, CELL_POINTS);
  public JTextField c[][] = new JTextField[9][];
  JButton solve = new JButton("solve");
  JButton clear = new JButton("clear");
  JButton exit = new JButton("exit");
  public int mat[][] = new int[9][9];

  public Interfata() {
    this.setTitle("Sudoku");
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        System.exit(0);
      }
    });

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    solve.setSize(50, 50);
    clear.setSize(50, 50);
    exit.setSize(50, 50);
    JPanel centerPanel = new JPanel();
    JPanel sudPanel = new JPanel();
    Container pane = this.getContentPane();
    pane.setLayout(new BorderLayout(0, 100));
    int gap = 5;
    centerPanel.setLayout(new GridLayout(3, 3, gap, gap));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
    centerPanel.setBackground(Color.black);
    
    JPanel[][] innerPanels = new JPanel[3][3];
    for (int i = 0; i < innerPanels.length; i++) {
      for (int j = 0; j < innerPanels[i].length; j++) {
        innerPanels[i][j] = new JPanel(new GridLayout(3, 3));
        centerPanel.add(innerPanels[i][j]);
      }
    }
        
    DocumentSizeFilter sizeFilter = new DocumentSizeFilter(1);
    for (int i = 0; i < 9; i++) {
      c[i] = new JTextField[9];
      for (int j = 0; j < 9; j++) {
        c[i][j] = new JTextField("", 3);
        ((PlainDocument)c[i][j].getDocument()).setDocumentFilter(sizeFilter);
        //panou_centru.add(c[i][j]);
        c[i][j].setPreferredSize(CELL_SIZE);
        innerPanels[i/3][j%3].add(c[i][j]);
        
        // TODO:  For testing purposes only.  Delete later
        c[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        c[i][j].setFont(CELL_FONT);
      }
    }
    add("Center", centerPanel);
    sudPanel.setLayout(new FlowLayout());
    sudPanel.add(solve);
    sudPanel.add(clear);
    sudPanel.add(exit);
    add("South", sudPanel);
  }

  private static void createAndShowUI() {
    JFrame frame = new Interfata();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        createAndShowUI();
      }
    });
  }
}

class DocumentSizeFilter extends DocumentFilter {
  private static final String ALLOWED = "123456789";
  int maxCharacters;
  boolean DEBUG = false;

  public DocumentSizeFilter(int maxChars) {
      maxCharacters = maxChars;
  }

  public void insertString(FilterBypass fb, int offs,
                           String str, AttributeSet a)
      throws BadLocationException {
      if (DEBUG) {
          System.out.println("in DocumentSizeFilter's insertString method");
      }

      if ((fb.getDocument().getLength() + str.length()) <= maxCharacters && ALLOWED.contains(str))
          super.insertString(fb, offs, str, a);
      else
          Toolkit.getDefaultToolkit().beep();
  }
  
  public void replace(FilterBypass fb, int offs,
                      int length, 
                      String str, AttributeSet a)
      throws BadLocationException {
      if (DEBUG) {
          System.out.println("in DocumentSizeFilter's replace method");
      }

      if ((fb.getDocument().getLength() + str.length()
           - length) <= maxCharacters && ALLOWED.contains(str))
          super.replace(fb, offs, length, str, a);
      else
          Toolkit.getDefaultToolkit().beep();
  }

}
