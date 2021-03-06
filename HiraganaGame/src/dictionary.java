import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class dictionary {

	public LinkedList<word> dic = new LinkedList<word>();
	
	public int max, min = 0;
	
	private Random random = new Random();
	
	public boolean loadDicFromTxt(String from, String to){
		dic.clear();
		try{
			BufferedReader brInFrom = new BufferedReader(new FileReader(from));
			BufferedReader brInTo = new BufferedReader(new FileReader(to));
			int k = 0;
			String line = "", line2 = "";
			
			while((line = brInFrom.readLine()) != null && (line2 = brInTo.readLine()) != null){
				line = line.trim().toLowerCase();
				line2 = line2.trim().toLowerCase();
				word w = new word(line, line2, false);
				dic.add(w);
				k++;
			}
			max = k - 1;
			brInFrom.close();
			brInTo.close();
			return true;
			
			
		}catch(IOException e2){
			System.out.println(e2);
			return false;
		}
		
	}
	
	public boolean loadDicFromCSV(String from, boolean ifChecked){
		dic.clear();
		
		try{
			BufferedReader brInFrom = new BufferedReader(new FileReader(from));
			
			int k = 0;
			String line = "";
			
			if(ifChecked){
				while((line = brInFrom.readLine()) != null){
					line = line.trim().toLowerCase();
					String[] words = line.split(",");
					word w = new word(words[0], words[1], false);
					dic.add(w);
					k++;
				}
			}else{
				while((line = brInFrom.readLine()) != null){
					line = line.trim().toLowerCase();
					String[] words = line.split(",");
					word w = new word(words[1], words[0], false);
					dic.add(w);
					k++;
				}
			}
			max = k - 1;
			brInFrom.close();
			return true;
			
			
		}catch(IOException e2){
			System.out.println(e2);
			return false;
		}
	}
	
	protected void addWrong(LinkedList<String> from, LinkedList<String> to){
		
		int k = 0;
		dic.clear();
		while(k < from.size() && k < to.size()){
			word w = new word(from.get(k), to.get(k), false);
			dic.add(w);
			k++;
		}
		max = k - 1;
		
	}
	
	protected String genRandomString() {
    	
		int i = 0; 
		boolean allFalse = true;
		String randomString = "0";
		if(dic.size() == 1) return dic.get(0).lang2;
		while(i < dic.size()){
			if(!dic.get(i).used) {
				allFalse = false; 
			}
			i++;
		}
		
		if(!allFalse){
			int k = genRandomNr();
			while(dic.get(k).used){
				k = genRandomNr();
			}
			randomString = dic.get(k).lang2;
			dic.get(k).setUsed(true);
		}
		
    	return randomString;
    	
    }
    
    protected int genRandomNr(){
    	int k = random.nextInt(max - min + 1) + min;
    	
    	return k;
    }	
    
    protected String getCorresponding(String k){
    	
    	for(int i = 0; i < dic.size(); i++)
    		if(k.equals(dic.get(i).lang2)) return dic.get(i).lang1;
    		
    	return "";
    }
    
    /*protected boolean has(String s, String k, boolean shouldBeTrue){
    	
    	//word w = new word(s,k,false);

		/*This should work with dic.contains(word), but somehow it doesn't*/
    	/*for(int i = 0; i < dic.size(); i++){
    		
    		
    		if(s.equals(dic.get(i).lang1) && k.equals(dic.get(i).lang2)) return true;
    	}
    		
    	
    	
    	
    	return false;
    }*/
    
    
}
