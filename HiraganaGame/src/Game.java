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

	private JButton btnSubmit, btnStart, btnRestart;
	private JTextField tfAnswer, tfTranslate;
	
	private JTextArea taResult;
	
	private JLabel lblAnswer, lblTranslate, lblResult, lblStart, lblAllResults;
	
	private JRadioButton rbHir, rbEng;
	
	private JScrollPane sp;
	
	private ButtonGroup bg = new ButtonGroup();
	
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	
	private int[] allResults = new int[100];
	
	final int size = 65;
	
	private String[] arrEng = new String[size];
	private String[] arrHir = new String[size];
	
	private int randomNr; 
	int nrOfCorr, min = 0, max = size - 1; 
	int nrDone = 0;
	boolean[] ifDone = new boolean[size];
	
	Random random = new Random();
	
	private String randomString = "";
	
	final JFrame frame = new JFrame();
	final JPanel panel = new JPanel();
	
	public Game() {
        initUI();
    }

    
	private void initUI() {
		
    	for(int i = 0; i < ifDone.length; i++) ifDone[i] = true;
    	
    	rbHir = new JRadioButton("Eng - Hir");
    	rbEng = new JRadioButton("Hir - Eng");
    	
    	btnSubmit = new JButton("Submit");
    	btnStart = new JButton("Start Game");
    	btnRestart = new JButton("Restart Game");
    	
    	btnRestart.setEnabled(false);
    	
    	btnSubmit.setEnabled(false);
    	
    	tfAnswer = new JTextField();
    	tfTranslate = new JTextField();
	 
    	taResult = new JTextArea("", 120, 600);
    	
    	tfTranslate.setEditable(false);
    	taResult.setEditable(false);
    	tfAnswer.setEditable(false);
    	
    	lblAnswer = new JLabel("Put your answer here:");
    	lblTranslate = new JLabel("Translate this: ");
    	lblResult = new JLabel("Right / Wrong");
    	lblStart = new JLabel("Choose hir-eng or eng-hir");
    	
    	lblAllResults = new JLabel("");
    	
    	bg.add(rbEng);
    	bg.add(rbHir);
    	
    	sp = new JScrollPane(taResult);
    	
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
    	
		//panel.add(btnStart);
    	panel.add(lblTranslate);
    	panel.add(tfTranslate);
    	
    	panel.add(lblAnswer);
    	panel.add(tfAnswer);
    	
    	panel.add(lblResult);
    	
    	panel.add(btnSubmit);
    	
    	frame.getContentPane().add(sp);
    	
    	frame.add(panel2);
    	frame.add(panel);
    	frame.add(panel3);
    	
    	frame.pack();
    	frame.setVisible(true);
        
    	frame.setTitle("Hiragana Game");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        btnStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rbHir.isSelected() || rbEng.isSelected()){
					if(rbHir.isSelected()) genArr(true);
					else genArr(false);
					tfAnswer.setEditable(true);
					btnSubmit.setEnabled(true);
					btnStart.setEnabled(false);
					btnRestart.setEnabled(true);
					genRandomString();
						
				}
			}

        	
        });
        
        btnRestart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tfAnswer.setEditable(false);
				btnSubmit.setEnabled(false);
				btnStart.setEnabled(true);
				btnRestart.setEnabled(false);
				taResult.setText(null);
				
				for(int i = 0; i < ifDone.length; i++) ifDone[i] = true;
			}
        	
        });
        
        btnSubmit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
					
				if(!tfAnswer.getText().equals("")){
					nrDone++;
					String line = tfAnswer.getText().trim();
					if(line.equals(arrEng[randomNr])){
						nrOfCorr++;
						taResult.append("Correct!         " + arrEng[randomNr] + " = " + arrHir[randomNr]);
					}else taResult.append("Wrong!         " + arrEng[randomNr] + " = " + arrHir[randomNr]);
					
					taResult.append("\n");
					taResult.append(nrOfCorr + " / " + size);
					ifDone[randomNr] = false;
					tfAnswer.setText("");
					taResult.append("\n");
					lblResult.setText("Your score : " + nrOfCorr + "/" + size + "  Done: " + nrDone);
					tfAnswer.requestFocus();
					genRandomString();
				}
			}
        });

    }
	/*Very wierd done, but it works for now..*/
    protected void genArr(boolean b) {
    	if(!b){
    		try{
    			BufferedReader brIn = new BufferedReader(new FileReader("hir.txt"));
    			String line = "";
    			int k = 0;
    			while((line = brIn.readLine()) != null){
    				if(!line.equals("")){
    					line = line.trim();
    					arrHir[k] = line;
    					k++;	
    				}
    			}
    		}catch(IOException e) {
    			System.out.println(e);
    		}
    		try{
    			BufferedReader brIn = new BufferedReader(new FileReader("eng.txt"));
    			String line = "";
    			int k = 0;
    			while((line = brIn.readLine()) != null){
    				if(!line.equals("")){
    					line = line.trim();
    					arrEng[k] = line;
    					k++;
    				}
    			}
    		}catch(IOException e) {
    			System.out.println(e);
    		}
    		
    	}else{
    		try{
    			BufferedReader brIn = new BufferedReader(new FileReader("eng.txt"));
    			String line = "";
    			int k = 0;
    			while((line = brIn.readLine()) != null){
    				if(!line.equals("")){
    					line = line.trim();
    					arrHir[k] = line;
    					k++;	
    				}
    			}
    		}catch(IOException e) {
    			System.out.println(e);
    		}
    		try{
    			BufferedReader brIn = new BufferedReader(new FileReader("hir.txt"));
    			String line = "";
    			int k = 0;
    			while((line = brIn.readLine()) != null){
    				if(!line.equals("")){
    					line = line.trim();
    					arrEng[k] = line;
    					k++;
    				}
    			}
    		}catch(IOException e) {
    			System.out.println(e);
    		}
    		
    	}
		
	}


	protected void genRandomString() {
    	randomNr = genRandomNr();
    	boolean done = true;
    	int k = 0;
    	while(!ifDone[randomNr]) {
    		k++;
    		randomNr = genRandomNr();
    		if(k > size * 3) {
    			done = false;
    			break;
    		}
    	}
    	if(!done){
    		int c = 0;
    		boolean end = true;
    		while(!ifDone[randomNr]){
    			c++;
    			randomNr = c;
    			if(randomNr == size) {
    				end = false; 
    				break;
    			}
    		}
    		if(end){	
    			randomString = arrHir[randomNr];
            	tfTranslate.setText(randomString);	
    		} else allDone();
    	}else {
			randomString = arrHir[randomNr];
        	tfTranslate.setText(randomString);	
    	}
    }
    
    protected int genRandomNr(){
    	return random.nextInt(max - min + 1) + min;
    }
    
    protected void allDone(){
    	JOptionPane.showMessageDialog(null, "Result : " + nrOfCorr + "/" + size );
    	nrOfCorr = 0;
    	//lblResult.setText("All done!");
    }

	public static void main(String[] args) {
    	Game ex = new Game();
                //ex.setVisible(true);
    }
            
	
}
