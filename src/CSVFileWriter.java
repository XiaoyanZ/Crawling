import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter {
	private static final String NEXT_LINE_SEPARATOR = "\n";
	private static String[] files;
	private static String path = "";
	
	//init output files
	public static void createFile(String newsSite) throws IOException{
		files = new String[]{path + "fetch_" + newsSite + ".csv",path + "visit_" + newsSite + ".csv",path + "urls_" + newsSite + ".csv"};
		String[][] file_header = new String[][]{
			{files[0], "URL_ATTEMPTED,HTTP_STATUS"},
			{files[1], "URL_SUCCESS,SIZE,#OUTLINKS,CONTENT-TYPE"},
			{files[2], "URL_ALL,RESIDENT_IN"}
		};
		FileWriter fw = null;
		for(String[] arr: file_header){
			try {
				fw = new FileWriter(arr[0]);
				fw.append(arr[1]);
				fw.append(NEXT_LINE_SEPARATOR);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("create file: " + arr[0] + " failed");
			} finally {
				fw.flush();
				fw.close();
			}
		}
	}
	
	//write output files
	public static synchronized void writeFile(String str, int tag) throws IOException{
		if(tag < 0 || tag > files.length - 1){
			System.out.println("tag info error");
			return;
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(files[tag],true);
			fw.append(str);
			fw.append(NEXT_LINE_SEPARATOR);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("create file " + files[tag] + " error" );
		} finally {
			fw.flush();
			fw.close();
		}
	}
}
