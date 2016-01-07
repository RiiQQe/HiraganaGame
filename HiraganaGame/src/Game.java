import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class Game extends JFrame{

	private JButton btnSubmit, btnStart, btnRestart, btnCorr;
	private JTextField tfAnswer, tfTranslate, tfEng, tfHir;
	
	private JTextArea taResult;
	
	String hirText, engText;
	
	private JLabel lblAnswer, lblTranslate, lblResult, lblStart, lblAllResults, lblMine;
	
	private JRadioButton rbHir, rbEng;
	
	private JScrollPane sp;
	
	private ButtonGroup bg = new ButtonGroup();
	
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	
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
	
	public Game() {
        initUI();
    }

    
	private void initUI() {
    	rbHir = new JRadioButton("Eng - Hir");
    	rbEng = new JRadioButton("Hir - Eng");
    	
    	btnSubmit = new JButton("Submit");
    	btnStart = new JButton("Start Game");
    	btnRestart = new JButton("Restart Game");
    	
    	btnRestart.setEnabled(false);
    	
    	btnSubmit.setEnabled(false);
    	
    	tfAnswer = new JTextField();
    	tfTranslate = new JTextField();
    	tfHir = new JTextField("test2",15);
    	tfEng = new JTextField("test1",15);
	 
    	taResult = new JTextArea("", 120, 600);
    	
    	tfTranslate.setEditable(false);
    	taResult.setEditable(false);
    	tfAnswer.setEditable(false);
    	
    	lblAnswer = new JLabel("Put your answer here:");
    	lblTranslate = new JLabel("Translate this: ");
    	lblResult = new JLabel("Right / Wrong");
    	lblStart = new JLabel("Choose hir-eng or eng-hir");
    	lblMine = new JLabel("Creator: Rickard Lindstedt");
    	
    	lblAllResults = new JLabel("");
    	
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
    	panel2.add(lblAllResults);
    	
    	panel2.add(tfEng);
    	panel2.add(tfHir);
    	
    	panel.add(lblTranslate);
    	panel.add(tfTranslate);
    	
    	panel.add(lblAnswer);
    	panel.add(tfAnswer);
    	
    	panel.add(btnCorr);
    	
    	panel.add(btnSubmit);
    	
    	panel3.add(lblMine);
    	
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
					
					hirText = tfHir.getText() + ".txt";
					engText = tfEng.getText() + ".txt";
					
					if(rbHir.isSelected()) dic.loadDic(engText, hirText);
					
					else dic.loadDic(hirText, engText);
					
					tfAnswer.setEditable(true);
					btnSubmit.setEnabled(true);
					btnStart.setEnabled(false);
					btnRestart.setEnabled(true);
					tfHir.setEnabled(false);
					tfEng.setEnabled(false);
					
					taResult.append("Size of arr: " + dic.dic.size());
					
					randomString = dic.genRandomString();
					
					tfTranslate.setText(randomString);
				}
			}
        });
        
        btnRestart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dic.dic.clear();
				dic.max = 0;
				resetVars();
		    	
				tfAnswer.setEditable(false);
				btnSubmit.setEnabled(false);
				btnStart.setEnabled(true);
				btnRestart.setEnabled(false);
				taResult.setText(null);
				
				tfTranslate.setText("");
				tfAnswer.setEditable(false);

				tfHir.setEnabled(true);
				tfEng.setEnabled(true);
				
				btnCorr.setBackground(Color.GRAY);
				lblResult.setText("Your score : " + nrOfCorr + "/" + size + "  Done: " + nrOfDone);
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
						taResult.append(ans + " is CORRECT!         " + ans + " = " + tr);
						btnCorr.setBackground(Color.GREEN);
					}else{
						nrOfWrong++;
						arrHirWrong.add(dic.getCorresponding(tr));
						arrEngWrong.add(tr);
						taResult.append(ans + " is WRONG!         " + dic.getCorresponding(tr) + " = " + tr);
				    	btnCorr.setBackground(Color.RED);
					}
					
					if(nrOfCorr == nrOfDone && nrOfDone == dic.dic.size()){
						resetVars();
						JOptionPane.showMessageDialog(null, "Well done, Lesson finished!");
						taResult.setText("You need to restart the game!!");
					}else if(nrOfCorr < nrOfDone && nrOfDone == dic.dic.size()){
						
						int reply = JOptionPane.showConfirmDialog(null, "Wanna try your misses again?", nrOfWrong + "/" + dic.dic.size(), JOptionPane.YES_NO_OPTION);
						resetVars();
						tfAnswer.setText("");
						if (reply == JOptionPane.YES_OPTION) {
				            dic.addWrong(arrHirWrong, arrEngWrong);
				            
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
