import crawler.CrawlerController;

public class Main {

    public static void main(String[] args) {
        String url = "http://wiprodigital.com";

        CrawlerController crawlerController = new CrawlerController();
        crawlerController.execute(url);
    }
}
