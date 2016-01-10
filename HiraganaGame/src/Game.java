import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;

public class Game extends JFrame{
	
	/*GuiStuff*/
	private JButton btnSubmit, btnStart, btnRestart, btnCorr, btnMine, btnFileChooser;
	private JTextField tfAnswer, tfTranslate, tfFrom, tfTo;
	
	private JTextArea taResult;
	
	private JLabel lblAnswer, lblTranslate, lblStart, lblMine;
	
	private JRadioButton rbTo, rbFrom, rbCSV, rbTxt;
	
	private JScrollPane sp;
	
	private ButtonGroup bg = new ButtonGroup();
	private ButtonGroup bgFiles = new ButtonGroup();
	
	
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	private final JFrame frame = new JFrame();
	private final JPanel panel = new JPanel();
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	
	/*Vars*/
	private boolean filesFound; 
	
	private String toText, fromText;
	private String toCSV, fromCSV;
	private File fromFile, toFile;
	
	int size;
	int nrOfWrong = 0;
	
	private LinkedList<String> arrFromWrong = new LinkedList<String>();
	private LinkedList<String> arrtoWrong = new LinkedList<String>();
	
	int nrOfCorr, min = 0, max; 
	int nrOfDone = 0;
	boolean[] ifDone;
	
	Random random = new Random();
	
	private String randomString = "";
	
	private float result;
	
	private dictionary dic = new dictionary();
	
	private String pathToLogo = "Logo.png";
	
	private boolean fileChoosen = false;
	
	public Game() {
        initUI();
    }

    
	private void initUI() {
		ImageIcon logo = new ImageIcon(pathToLogo);
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("Info");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		
		menuBar.add(menu);
		//a group of JMenuItems
		menuItem = new JMenuItem("How to use",
		                         KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "This doesn't really do anything");
		menu.add(menuItem);
		
		rbCSV = new JRadioButton(".csv");
		rbTxt = new JRadioButton(".txt");
		
		rbTxt.setSelected(true);
		
    	rbTo = new JRadioButton("from - to");
    	rbFrom = new JRadioButton("to - from");
    	
    	btnSubmit = new JButton("Submit");
    	btnStart = new JButton("Start Game");
    	btnRestart = new JButton("Restart Game");
    	btnMine = new JButton("Go to my website!");
    	btnFileChooser = new JButton("Choose files");
    	
    	btnMine.setBorderPainted(false);
    	btnMine.setFocusPainted(false);
    	btnMine.setContentAreaFilled(false);
    	
    	btnRestart.setEnabled(false);
    	
    	btnSubmit.setEnabled(false);
    	
    	btnMine.setEnabled(true);
    	
    	tfAnswer = new JTextField();
    	tfTranslate = new JTextField();
    	tfTo = new JTextField("kap7eng",15);
    	tfFrom = new JTextField("kap7hir",15);
	 
    	taResult = new JTextArea("", 120, 600);
    	
    	tfTranslate.setEditable(false);
    	taResult.setEditable(false);
    	tfAnswer.setEditable(false);
    	
    	lblAnswer = new JLabel("Put your answer here:");
    	lblTranslate = new JLabel("Translate this: ");
    	lblStart = new JLabel("Choose to-from or from-to");
    	lblMine = new JLabel("Creator: Rickard Lindstedt");
    	
    	frame.setIconImage(logo.getImage());
    	
    	rbTo.setSelected(true);
    	
    	bg.add(rbFrom);
    	bg.add(rbTo);
    	
    	bgFiles.add(rbCSV);
    	bgFiles.add(rbTxt);
    	
    	sp = new JScrollPane(taResult);
    	btnCorr = new JButton("CORR = GREEN, WRONG = RED");
    	btnCorr.setEnabled(false);
    	btnCorr.setBackground(Color.GRAY);
    	
		//Setting layout for the panel
		panel.setLayout(new GridLayout(8,2));
		frame.setLayout(new GridLayout(3,1));
		/*THIS IS HOW IT WAS*/
    	panel2.add(btnStart);
    	panel2.add(rbFrom);
    	panel2.add(rbTo);
    	panel2.add(lblStart);
    	panel2.add(btnStart);
    	panel2.add(btnRestart);
    	
    	
    	panel2.add(tfFrom);
    	panel2.add(tfTo);
    	
    	panel2.add(btnFileChooser);
    	
    	panel2.add(rbCSV);
    	panel2.add(rbTxt);
    	
    	
    	panel.add(lblTranslate);
    	panel.add(tfTranslate);
    	
    	panel.add(lblAnswer);
    	panel.add(tfAnswer);
    	
    	panel.add(btnCorr);
    	
    	panel.add(btnSubmit);
    	
    	panel3.add(lblMine);
    	panel3.add(btnMine);
    	
    	frame.getContentPane().add(sp);
    	
    	frame.add(panel2);
    	frame.add(panel);
    	frame.add(panel3);
    	
    	frame.pack();
    	frame.setVisible(true);
        
    	frame.setTitle("Vocabulary Game");
        frame.setSize(1000, 900);
        
        frame.setLocationRelativeTo(null);
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getRootPane().setDefaultButton(btnStart);
        
        frame.setJMenuBar(menuBar);
        
        btnStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rbTo.isSelected() || rbFrom.isSelected()){
					
					resetVars();
					if(!fileChoosen && rbTxt.isSelected()){
						toText = tfTo.getText();
						fromText = tfFrom.getText();
						
						toText = toText.replace(".txt", "");
						fromText = fromText.replace(".txt", "");
						
						toText = toText + ".txt";
						fromText = fromText + ".txt";
					}
					if(rbCSV.isSelected()){
						fromCSV = tfFrom.getText();
						fromCSV = fromCSV.replace(".csv", "");
						fromCSV = fromCSV + ".csv";
					}
					
					if(rbTo.isSelected() && rbTxt.isSelected()){
						if(!dic.loadDicFromTxt(fromText, toText)){
							JOptionPane.showMessageDialog(null, "Couldn't find the files");
							filesFound = false;
						}else filesFound = true;
					}
					else if(rbFrom.isSelected() && rbTxt.isSelected()){
						if(!dic.loadDicFromTxt(toText, fromText)){
							JOptionPane.showMessageDialog(null, "Couldn't find the files");
							filesFound = false;
						}else filesFound = true;
					}
					else if(rbTo.isSelected() && rbCSV.isSelected()){
						if(!dic.loadDicFromCSV(fromCSV, true)){
							JOptionPane.showMessageDialog(null, "Couldn't find the file");
							filesFound = false;
						}else filesFound = true;
					}
					else if(rbFrom.isSelected() && rbCSV.isSelected()){
						if(!dic.loadDicFromCSV(fromCSV, false)){
							JOptionPane.showMessageDialog(null, "Couldn't find the file");
							filesFound = false;
						}else filesFound = true;
					}
					
					if(filesFound){
						tfAnswer.requestFocus();
						frame.getRootPane().setDefaultButton(btnSubmit);
						btnFileChooser.setEnabled(false);
						tfAnswer.setEditable(true);
						btnSubmit.setEnabled(true);
						btnStart.setEnabled(false);
						btnRestart.setEnabled(true);
						tfTo.setEnabled(false);
						tfFrom.setEnabled(false);
						
						taResult.append("Size of arr: " + dic.dic.size());
						
						randomString = dic.genRandomString();
						
						tfTranslate.setText(randomString);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please check one of the above radiobuttons");
				}
			}
        });
        
        btnRestart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restart();
			}
        	
        });
        
        btnSubmit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!tfAnswer.getText().equals("")){
					taResult.append("\n");
					taResult.append("remaining: " + (dic.dic.size() - 1 - nrOfDone));
					taResult.append("\n");
					
					String tr = tfTranslate.getText().trim().toLowerCase();
					String ans = tfAnswer.getText().trim().toLowerCase();
					
					nrOfDone++;
					if(dic.getCorresponding(tr).equals(ans)){
						nrOfCorr++;
						taResult.append(ans + " is CORRECT!         " + ans + " = " + tr + "\n");
						btnCorr.setBackground(Color.GREEN);
					}else{
						nrOfWrong++;
						arrtoWrong.add(dic.getCorresponding(tr));
						arrFromWrong.add(tr);
						taResult.append(ans + " is WRONG!         " + dic.getCorresponding(tr) + " = " + tr + "\n");
						btnCorr.setBackground(Color.RED);
					}
					
					taResult.append("NrOfWrong: " + nrOfWrong + "\n");
					
					if(nrOfCorr == nrOfDone && nrOfDone == dic.dic.size()){
						resetVars();
						JOptionPane.showMessageDialog(null,"Well done, Lesson finished!");
						restart();
					}else if(nrOfCorr < nrOfDone && nrOfDone == dic.dic.size()){
						Color c = getColor();
						btnCorr.setBackground(c);
						int reply = JOptionPane.showConfirmDialog(null, "Wanna try your misses again? Percentage corr: " + result * 100 + "%", nrOfWrong + "/" + dic.dic.size(), JOptionPane.YES_NO_OPTION);
						resetVars();
						tfAnswer.setText("");
						if (reply == JOptionPane.YES_OPTION) {
				            dic.addWrong(arrtoWrong, arrFromWrong);
				            arrtoWrong.clear();
				            arrFromWrong.clear();
				            tfTranslate.setText(dic.genRandomString());
				        }
				        else {
				        	restart();
				        }
					}else{
						tfTranslate.setText(dic.genRandomString());
						tfAnswer.setText("");
						tfAnswer.requestFocus();
					}
				}
			}
        });
        
        btnFileChooser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				final JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose file to translate from");
				int returnVal = fc.showOpenDialog(Game.this);
				
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					toFile = fc.getSelectedFile();
					
					toText = toFile.getPath();
					String fileName = "";
					if(!rbCSV.isSelected()) {
						fileName = toFile.getName().replace(".txt", "");
						tfTo.setText(fileName);
					}
					else {
						fileName = toFile.getName().replace(".csv", "");
						tfFrom.setText(fileName);
					}
					
				}
				if(!rbCSV.isSelected()){
					returnVal = fc.showOpenDialog(Game.this);
					fc.setDialogTitle("Choose file with the translations");
					
					if(returnVal == JFileChooser.APPROVE_OPTION){
						fromFile = fc.getSelectedFile();
						
						fromText = fromFile.getPath();
						
						tfFrom.setText(fromFile.getName().replace(".txt", ""));
						fileChoosen = true;
					}
				}
			}
        	
        });
        
        
        btnMine.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					URL url = new URL("http://www.rickardlindstedt.com");
			        openWebpage(url.toURI());
			    } catch (URISyntaxException e1) {
			        e1.printStackTrace();
			    } catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
        	
        });
        
        menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This is how you use it...");
				System.out.println("hesjan");
				
			}
        	
        });
        
        rbCSV.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(rbCSV.isSelected()){
					tfTo.setEnabled(false);
				}
				
			}
        	
        });
        
        rbTxt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rbTxt.isSelected()){
					tfTo.setEnabled(true);
				}
				
			}
        	
        });
        
	}
	
	
	
	public void restart(){
		dic.dic.clear();
		dic.max = 0;
		resetVars();
		frame.getRootPane().setDefaultButton(btnStart);
    	
		btnFileChooser.setEnabled(true);
		tfAnswer.setEditable(false);
		btnSubmit.setEnabled(false);
		btnStart.setEnabled(true);
		btnRestart.setEnabled(false);
		taResult.setText(null);
		
		tfTranslate.setText("");
		tfAnswer.setEditable(false);
		tfAnswer.setText("");
		tfTo.setEnabled(true);
		tfFrom.setEnabled(true);
		
		btnCorr.setBackground(Color.GRAY);
		
		tfFrom.requestFocus();
		if(rbCSV.isSelected())
		tfTo.setEnabled(false);
		
	}
	
	public Color getColor(){
		
		if(nrOfDone == 0) return Color.GRAY;
		result = (float)nrOfCorr / nrOfDone;
		result = Float.parseFloat(new DecimalFormat("##.##").format(result).replace(",", "."));
	
		if(result < 0.33) return Color.RED;
		if(result >= 0.33 && result <= 0.66) return Color.ORANGE;
		if(result > 0.66) return Color.GREEN;
		
		return Color.GRAY;
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	protected void resetVars(){
		nrOfCorr = 0;
		nrOfDone = 0;
		nrOfWrong = 0;
	}
	  
	public static void main(String[] args) {
    	Game ex = new Game();
    }
            
	
}
