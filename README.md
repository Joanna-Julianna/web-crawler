Web Crawler Project
===============================

Prerequisites
-------------
- JDK 8
- Gradle use `gradlew` wrapper


Building with Gradle
--------------------
Type:

    ./gradlew clean build


TODO
--------------------
1. Interrupt the execution (define maximum number of sites to visit)

2. Remove duplicates with '#' like:
https://wiprodigital.com/who-we-are
https://wiprodigital.com/who-we-are/#masthead
https://wiprodigital.com/who-we-are/#wdteam-vid

3. Take disallowed sites from robots.txt only for 'User-agent: *'

4. Create sitemap

5. Add priority for sites

6. Think about better algorithm