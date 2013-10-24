HappyCrawl
==========

What's this:

An easy and clearly to use framework to let you focus on information extract process instead of how to fetch webpages. Usually web info mining can be tedious due to unclear mining logic. In this framework, roles are set to make the extract process clear and clean.

The whole process seems like MapReduce.

There are three roles in this framework. 

1. A Pager is responding for store a page and extract infomation locally to key-value pairs. A Pager only need to initialized with a URI

2. A Fetcher will fetch a collection of pagers based on their URI. URI means these page can both on local disk or from web. The fetching process is in multi-threads.

3. A Combiner will scan a list of Pagers fetched and combine all the key:value pairs as you will.


Powered by Jsoup, you can use css selector in your code to select the element(s).

Demo:
Here is a page contains a list of URLs which direct me to some interesting articles. So I want to start with this page and collect all the articles into one HTML file which may be coverted into a PDF or something else. But a have download the content page into my local disk

1. So I derived a FilePager constructed with the path to the file. When override the convert() method, I select all the &ltdiv&gt with special selector and output them with the key of the index of thire occurence.

2. Then I implements a Combiner to combine all the previous together in order of their occurence.(This combiner is not necessary, infact you can directly handle the output in main function)

3. Use the combined info, I instance many other new Pagers which is responding to extract only the &ltdiv&gt contains the article in the page.

4. Implements a new Combiner to collect all the &ltdiv&gts to one HTML file.

TODO:

1. Use maven to manage JARs;

2. Add local cache for HttpParser, so when debugging the program, it need not to crawl the web everytime.

3. Add common Pagers and Combiners
