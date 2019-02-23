package pl.joanna.webcrawler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.joanna.webcrawler.crawler.CrawlerService;

import java.util.Set;

@SpringBootApplication
public class CrawlerApplication implements CommandLineRunner {

    private final CrawlerService crawlerService;

    public CrawlerApplication(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Set<String> crawledSites = crawlerService.findAllSites("http://wiprodigital.com");
        System.out.println("Crawler found " + crawledSites.size() + " sites");
    }

}
