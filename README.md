Web Crawler Project
===============================

About project
-------------
The crawler is limited to one domain and visit all pages within the domain and find content as links for other websites and images etc.

The Frontier contains set of crawled sites. There are sites, we visited and links to external sites.

Crawler is executed through executor in many threads. There was one main problem. For some time there was a situation, when the next site to follow was empty, so I couldn't finish the execution, despite that the queue of unvisited sites was empty. I add counter and continue until last thread finish execution (to be precise: finish looking for links). I also call "sleep", because I think is better wait some time (one second) until other thread finds links to follow, than run crawler all the time unsuccessfully.


Prerequisites
-------------
- JDK 12


Building with Gradle
--------------------
Type:

    ./gradlew clean build


Running jar
-------------

Type:

    java -jar build/libs/crawler-1.0-SNAPSHOT.jar


Run using IDE
-------------

Open class CrawlerApplication and run


What would I do if I have got more time
--------------------
1. Add profile for integration tests

2. Define maximum depth and interrupt the execution

3. Remove duplicates with '#' like:
https://wiprodigital.com/who-we-are
https://wiprodigital.com/who-we-are/#masthead
https://wiprodigital.com/who-we-are/#wdteam-vid

4. Remove duplicate for the same page with protocol http and https

5. Take disallowed sites from robots.txt only for 'User-agent: *'

6. Add parameterization like e.g.: 
    CrawlerParameter.builder()
              .withThreads(nThreads)
              .withDepth(maxDepth)
              .withFollowToDifferentDomain(true)
              .build();

7. Implement retrying algorithm for failed requests

8. Create sitemap, add priority for sites 
