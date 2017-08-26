import java.io.*;
import java.util.HashSet;

public class Agent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String csvFile = "urls_NY_Times.csv";
	        BufferedReader br = null;
	        String line = "";
	        int all = 0, unique = 0, indomain = 0, outdomain = 0;
	        HashSet<String> uniqueURL = new HashSet<String>();
	    	String domainHttp = "http://www.nytimes.com/";
	    	String domainHttps = "https://www.nytimes.com/";
	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String url = line.split(",")[0];
	                all = all + 1;
	                if(uniqueURL.add(url)){
	                	unique = unique + 1;
	                	if(url.startsWith(domainHttp) || url.startsWith(domainHttps))
	                		indomain = indomain + 1;
	                	else
	                		outdomain = outdomain + 1;
	                }
	                

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        System.out.println("all is: "  + all);
	        System.out.println("unique is: "  + unique);
	        System.out.println("indomain is: "  + indomain);
	        System.out.println("outdomain is: "  + outdomain);

	    }
	}


