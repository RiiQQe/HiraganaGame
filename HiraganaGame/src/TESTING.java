
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		btnFileChooser = new JButton("File chooser");
		
		panel.setLayout(new GridLayout(3,2));
		panel.add(btnFileChooser);
		
		taResult = new JTextArea("", 120, 0);

		sp = new JScrollPane(taResult);
		
		panel.add(sp);
		
		frame.setLayout(new GridLayout(3,2));
		
		frame.add(panel);
		
		frame.pack();
    	frame.setVisible(true);
        
    	frame.setTitle("Vocabulary Game");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        System.out.println("hejsan");
    }
	
	
	public static void main(String[] args) {
    	TESTING ex = new TESTING();
    }
            
	
}

