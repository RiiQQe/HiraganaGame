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
import java.util.*;

import javax.swing.*;

public class Game extends JFrame{

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
	
	private boolean kalle; // denna ska bort, fullösning för nu
	
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
	
	private dictionary dic = new dictionary();
	
	private String pathToLogo = "Logo.png";
	
	public Game() {
        initUI();
    }

    
	private void initUI() {
		
		ImageIcon logo = new ImageIcon(pathToLogo);
		
    	rbHir = new JRadioButton("Eng - Hir");
    	rbEng = new JRadioButton("Hir - Eng");
    	
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
    	tfHir = new JTextField("kap7eng",15);
    	tfEng = new JTextField("kap7hir",15);
	 
    	taResult = new JTextArea("", 120, 600);
    	
    	tfTranslate.setEditable(false);
    	taResult.setEditable(false);
    	tfAnswer.setEditable(false);
    	
    	lblAnswer = new JLabel("Put your answer here:");
    	lblTranslate = new JLabel("Translate this: ");
    	lblStart = new JLabel("Choose hir-eng or eng-hir");
    	lblMine = new JLabel("Creator: Rickard Lindstedt");
    	
    	frame.setIconImage(logo.getImage());
    	
    	bg.add(rbEng);
    	bg.add(rbHir);
    	
    	sp = new JScrollPane(taResult);
    	btnCorr = new JButton("CORR = GREEN, WRONG = RED");
    	btnCorr.setEnabled(false);
    	btnCorr.setBackground(Color.GRAY);
    	
		//Setting layout for the panel
		panel.setLayout(new GridLayout(8,2));
		frame.setLayout(new GridLayout(3,1));
		/*THIS IS HOW IT WAS*/
    	panel2.add(btnStart);
    	panel2.add(rbEng);
    	panel2.add(rbHir);
    	panel2.add(lblStart);
    	panel2.add(btnStart);
    	panel2.add(btnRestart);
    	
    	
    	panel2.add(tfEng);
    	panel2.add(tfHir);
    	
    	panel2.add(btnFileChooser);
    	
    	
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
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        btnStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rbHir.isSelected() || rbEng.isSelected()){
					
					resetVars();
					
					hirText = tfHir.getText();
					engText = tfEng.getText();
					
					hirText = hirText.replace(".txt", "");
					engText = engText.replace(".txt", "");
					
					hirText = hirText + ".txt";
					engText = engText + ".txt";
					
					if(rbHir.isSelected()) dic.loadDic(engText, hirText);
					
					else dic.loadDic(hirText, engText);
					
					btnFileChooser.setEnabled(false);
					tfAnswer.setEditable(true);
					btnSubmit.setEnabled(true);
					btnStart.setEnabled(false);
					btnRestart.setEnabled(true);
					tfHir.setEnabled(false);
					tfEng.setEnabled(false);
					
					taResult.append("Size of arr: " + dic.dic.size());
					
					randomString = dic.genRandomString();
					
					tfTranslate.setText(randomString);
				}else{
					JOptionPane.showMessageDialog(null, "Please check one of the above radiobuttons");
				}
			}
        });
        
        btnRestart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dic.dic.clear();
				dic.max = 0;
				resetVars();
		    	
				btnFileChooser.setEnabled(true);
				tfAnswer.setEditable(false);
				btnSubmit.setEnabled(false);
				btnStart.setEnabled(true);
				btnRestart.setEnabled(false);
				taResult.setText(null);
				
				tfTranslate.setText("");
				tfAnswer.setEditable(false);
				tfAnswer.setText("");
				tfHir.setEnabled(true);
				tfEng.setEnabled(true);
				
				btnCorr.setBackground(Color.GRAY);
				
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
						arrHirWrong.add(dic.getCorresponding(tr));
						arrEngWrong.add(tr);
						taResult.append(ans + " is WRONG!         " + dic.getCorresponding(tr) + " = " + tr + "\n");
				    	btnCorr.setBackground(Color.RED);
					}
					
					taResult.append("NrOfWrong: " + nrOfWrong + "\n");
					
					if(nrOfCorr == nrOfDone && nrOfDone == dic.dic.size()){
						resetVars();
						JOptionPane.showMessageDialog(null,"Well done, Lesson finished!");
						taResult.setText("You need to restart the game!!");
					}else if(nrOfCorr < nrOfDone && nrOfDone == dic.dic.size()){
						
						int reply = JOptionPane.showConfirmDialog(null, "Wanna try your misses again?", nrOfWrong + "/" + dic.dic.size(), JOptionPane.YES_NO_OPTION);
						resetVars();
						tfAnswer.setText("");
						if (reply == JOptionPane.YES_OPTION) {
				            dic.addWrong(arrHirWrong, arrEngWrong);
				            arrHirWrong.clear();
				            arrEngWrong.clear();
				            tfTranslate.setText(dic.genRandomString());
				        }
				        else {
				        	taResult.setText("You need to restart the game!!");
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
					
					tfHir.setText(toFile.getName().replace(".txt", ""));
				}
				
				returnVal = fc.showOpenDialog(Game.this);
				fc.setDialogTitle("Choose file with the translations");
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					fromFile = fc.getSelectedFile();
					tfEng.setText(fromFile.getName().replace(".txt", ""));
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
		System.out.println("Resetar values");
		nrOfCorr = 0;
		nrOfDone = 0;
		nrOfWrong = 0;
	}
	  
	public static void main(String[] args) {
    	Game ex = new Game();
    }
            
	
}
