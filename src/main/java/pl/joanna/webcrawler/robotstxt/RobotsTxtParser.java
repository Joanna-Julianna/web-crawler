package pl.joanna.webcrawler.robotstxt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RobotsTxtParser {

    private static final Logger LOG = LoggerFactory.getLogger(RobotsTxtParser.class);

    private static final String DISALLOW = "Disallow:";

    /**
     * Find disallowed pages for given url.
     */
    public Set<String> findDisallowedPages(String url) {
        String robotsTxtUrl = url + "/robots.txt";
        try {
            Document document = Jsoup.connect(robotsTxtUrl).get();
            String body = document.wholeText();
            return parse(body);
        } catch (IOException e) {
            LOG.warn("For '{}': {}", url, e.getMessage());
            return new HashSet<>();
        }
    }

    /**
     * Find disallowed pages for given page content.
     */
    Set<String> parse(String body) {
        String[] lines = body.split("\n");
        return Stream.of(lines)
                .filter(line -> line.startsWith(DISALLOW))
                .map(line -> line.replaceFirst(DISALLOW, "").trim())
                .collect(Collectors.toSet());
    }
}
