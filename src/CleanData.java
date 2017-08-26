import java.io.*;
import java.util.*;

public class CleanData {

	public static void main(String[] args) throws IOException {
		File folder = new File("/Users/xiaoyan/Desktop/output_TheJewelofSevenStars.txt");
		
    	BufferedReader br = new BufferedReader(new FileReader(folder));
    	HashMap<String, String> hm = new HashMap<String, String>();
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		if(!line.isEmpty()){
	    		String[] parts = line.split("\t",2);
	    		String word = parts[0];
	    		String content = parts[1].replaceAll("\t+$","");
	    		hm.put(word, content);
	    	}
    	}
    	
    	
		File folder2 = new File("/Users/xiaoyan/Desktop/output_master.txt");
		
    	BufferedReader br2 = new BufferedReader(new FileReader(folder2));
    	line = null;
    	while ((line = br2.readLine()) != null) {
    		if(!line.isEmpty()){
	    		String[] parts = line.split("\t");
	    		String word = parts[0];
	    		String text = parts[1].replaceAll("\t+$","");
	    		String content = hm.getOrDefault(word, null);
	    		if(content != null){
	    			if(!content.equals(text)){
	    				String s = "["+ word +"][INPUT2\t" + text + "\t][INPUT1\t" + content + "]";
	    				System.out.println(s);
	    			}
	    		}

	    	}
    	}
    	System.out.println("well done");
	}

}
