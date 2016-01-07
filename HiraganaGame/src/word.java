
public class word {

	/*Member variables*/
	public String lang1, lang2;
	public boolean used;
	
	/*Constructor*/
	public word(String l1, String l2, boolean u) {
        lang1 = l1;
        lang2 = l2;
        used = u;
    }
	/*Set variables*/
	public void setL1(String l1){
		lang1 = l1;
	}
	public void setL2(String l2){
		lang2 = l2;
	}
	public void setUsed(boolean u){
		used = u;
	}
	
	/*Get variables*/
	public String getL1(){
		return lang1;
	}
	public String getL2(){
		return lang2;
	}
	public boolean getUsed(){
		return used;
	}
}
