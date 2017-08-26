
import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.*;

public class CrawlerController {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "data/crawl";
//        String[] fileNames = new String[]{"fetch_NY_Times.csv","visit_NY_Times.csv","urls_NY_Times.csv"}; 
        int numberOfCrawlers = 7;
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(16);
        config.setMaxPagesToFetch(20000);
        config.setIncludeHttpsPages(true);
        config.setIncludeBinaryContentInCrawling(true);
        /*
         * Instantiate the controller for this crawl.
         */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		/*
		* For each crawl, you need to add some seed urls. These are the first
		* URLs that are fetched and then the crawler starts following links
		* which are found in these pages
		*/
		controller.addSeed("http://www.nytimes.com/");

		/*
		 * Create output files
		 */
		CSVFileWriter.createFile("NY_Times");
		
		/*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(Crawler.class, numberOfCrawlers);
    }
}