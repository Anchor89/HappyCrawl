HappyCrawl
==========

An easy and clearly to use framework for crawling pages from web and extract useful informations.

What's this:
This framework is extremely simple while powerfull. This framework defines the roles and workflow of crawling, all you need to do is to set URL, write your own infomation extractor(anchor89.crawl.Pager here) and infomation integrator(anchor89.crawl.Combiner here).

The whole process seems like MapReduce.

Pager is respond for some pages. In a derived Pager, you need to override convert() method to orgnize the information you got to key:value pairs, which works like a Mapper. There are two different kinds of Pagers: HttpPager and FilePager who are responding to fetch page from web or local disk.
Then Combiner will scan a list of Pagers and use all the key:value pairs to produce something you want, which works like a reduce but the input are not orgnized by keys.

The crawling process is multi-thread, so it's fast.

Powered by Jsoup, you can use css selector in your code to select the element(s).

Demo:
Here is a page contains a list of URLs which direct me to some interesting articles. So I want to start with this page and collect all the articles into one HTML file which may be coverted into a PDF or something else. But a have download the content page into my local disk
1. So I derived a FilePager constructed with the path to the file. When override the convert() method, I select all the <div>s with special selector and output them with the key of the index of thire occurence.
2. Then I implements a Combiner to combine all the previous together in order of their occurence.(This combiner is not necessary, infact you can directly handle the output in main function)
3. Use the combined info, I instance many other new Pagers which is responding to extract only the <div> contains the article in the page.
4. Implements a new Combiner to collect all the <div>s to one HTML file.

TODO:
1. Use maven to manage JARs;
2. Add local cache for HttpParser, so when debugging the program, it need not to crawl the web everytime.