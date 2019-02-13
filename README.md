Web Crawler Project
===============================

Prerequisites
-------------
- JDK 8
- Gradle


Building with Gradle
--------------------
Type:

    ./gradlew clean build
    
How to run
-------------

You can run crawler through integration test "execute crawler" in class CrawlerControllerTest

TODO
--------------------
1. Interrupt the execution (define maximum number of sites to visit)

2. Remove duplicates with '#' like:
https://wiprodigital.com/who-we-are
https://wiprodigital.com/who-we-are/#masthead
https://wiprodigital.com/who-we-are/#wdteam-vid

3. Remove duplicate if the same page with protocol http and https

4. Take disallowed sites from robots.txt only for 'User-agent: *'

5. Create sitemap

6. Add priority for sites

7. Add first link to crawled

8. Add test for WebCrawler