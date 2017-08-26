import java.io.*;
import java.util.*;
import java.util.regex.*;
import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {
    private static final Pattern filters = Pattern.compile(
            ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|" +
            "|rm|smil|wmv|swf|wma|zip|rar|gz|rss|xml))$");
//	private static final Pattern patterns = Pattern.compile(".*(\\.(doc|html|pdf|bmp|gif|jpe?g|png|tiff?))$");
	private String domainHttp = "http://www.nytimes.com/";
	private String domainHttps = "https://www.nytimes.com/";
	private HashSet<String> uniqueURL = new HashSet<String>();
	private int all = 0, unique = 0, indomain = 0, outdomain = 0;
	
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription){
		try {
			CSVFileWriter.writeFile(webUrl.getURL().toLowerCase() + "," + statusCode, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
	    String newURL = url.getURL().toLowerCase();
	    if(!newURL.startsWith(domainHttp) && !newURL.startsWith(domainHttps))
	    	return false;
	    if(filters.matcher(newURL).matches())
	    	return false;
	    return true;
	}
	
	@Override
	public void visit(Page page) {
		ArrayList<String> list = new ArrayList<String>();
		String url = page.getWebURL().getURL();
		String contentType = page.getContentType().split(";")[0];
		list.add(url);
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			list.add(Integer.toString(html.length()));
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			list.add(Integer.toString(links.size()));
			Iterator<WebURL> ite = links.iterator();
			while(ite.hasNext()){
				String outURL = ite.next().getURL();
				try {
					all = all + 1;
					if(outURL.startsWith(domainHttp) || outURL.startsWith(domainHttps)){
						if(uniqueURL.add(outURL)){
							unique = unique + 1;
							indomain = indomain + 1;
						}
						CSVFileWriter.writeFile(outURL + ",OK,all:" + all + ",unique:" + unique + ",indomain:" + indomain + ",outdomain:" + outdomain, 2);
					}else{
						if(uniqueURL.add(outURL)){
							unique = unique + 1;
							outdomain = outdomain + 1;
						}
						CSVFileWriter.writeFile(outURL + ",N_OK,all:" + all + ",unique:" + unique + ",indomain:" + indomain + ",outdomain:" + outdomain, 2);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			list.add("0");
			list.add("0");
		}
		list.add(contentType);
		if(contentType != "application/json" && contentType != "text/xml")
			try {
				CSVFileWriter.writeFile(String.join(",", list), 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
