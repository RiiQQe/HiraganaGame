
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class TESTING extends JFrame{

	private JButton btnSubmit, btnStart, btnRestart, btnCorr, btnMine, btnFileChooser;
	private JTextField tfAnswer, tfTranslate, tfEng, tfHir;
	
	private JTextArea taResult;
	
	String hirText, engText;
	
	File fromFile, toFile;
	
	private JLabel lblAnswer, lblTranslate, lblStart, lblAllResults, lblMine;
	
	private JRadioButton rbHir, rbEng;
	
	private JScrollPane sp;
	
	private ButtonGroup bg = new ButtonGroup();
	
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuCSVFile, menuTxtFile;
	
	private boolean filesFound; 
	
	int size;
	int nrOfWrong = 0;
	
	private LinkedList<String> arrEngWrong = new LinkedList<String>();
	private LinkedList<String> arrHirWrong = new LinkedList<String>();
	
	int nrOfCorr, min = 0, max; 
	int nrOfDone = 0;
	boolean[] ifDone;
	
	Random random = new Random();
	
	private String randomString = "";
	
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	
	private Color[] colors = new Color[3];
	private float result;
	
	private dictionary dic = new dictionary();
	
	private String pathToLogo = "Logo.png";
	
	public TESTING() {
        initUI();
    }

    
	private void initUI() {
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		
		menuBar.add(menu);
		//a group of JmenuCSVFiles
		menuCSVFile = new JMenuItem("Add CSV-file",
		                         KeyEvent.VK_T);
		menuCSVFile.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuCSVFile.getAccessibleContext().setAccessibleDescription(
		        "This doesn't really do anything");
		menu.add(menuCSVFile);
		
		menuTxtFile = new JMenuItem("Add TXT-files",
                KeyEvent.VK_T);
		menuTxtFile.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuTxtFile.getAccessibleContext().setAccessibleDescription(
		"This doesn't really do anything");
		menu.add(menuTxtFile);
		
		tfTranslate = new JTextField("hejsan");
		tfAnswer = new JTextField("Answer here");
		tfTranslate = new JTextField("Translate this: ");
		btnSubmit = new JButton("Submit");
		btnMine = new JButton("Creator: Rickard Lindstedt");
		btnStart = new JButton("Start");
		
		tfTranslate.setEnabled(false);
		
		btnMine.setBorderPainted(false);
    	btnMine.setFocusPainted(false);
    	btnMine.setContentAreaFilled(false);
		
		panel.setLayout(new GridLayout(3,2));
		
		panel2.setLayout(new GridLayout(3,2));
		
		panel3.setLayout(new GridLayout());
		
		taResult = new JTextArea("", 500, 0);
		taResult.setEditable(false);
		
		sp = new JScrollPane(taResult);
		
		panel.add(sp);
		panel.add(tfTranslate);
		panel.add(tfAnswer);
		
		
		panel2.add(btnSubmit);
		
		panel3.add(btnMine);
		
		frame.setLayout(new GridLayout(3,2));
		
		frame.add(panel);
		frame.add(panel2);
		frame.add(panel3);
		
		frame.pack();
    	frame.setVisible(true);
        
    	frame.setTitle("Vocabulary Game");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.setJMenuBar(menuBar);
        
        menuTxtFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }
	
	
	
	
	public static void main(String[] args) {
    	TESTING ex = new TESTING();
    }
            
	
}

