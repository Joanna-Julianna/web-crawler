package crawler;

public class WebCrawlerRunnable implements Runnable {

    private WebCrawler webCrawler;

    private final String url;

    public WebCrawlerRunnable(WebCrawler webCrawler, String url) {
        this.webCrawler = webCrawler;
        this.url = url;
    }

    @Override
    public void run() {
        webCrawler.getPageLinks(url);
        webCrawler.decrementThreads();
    }
}
