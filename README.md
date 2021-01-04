# prettyurl
To re-generate the long link into the shorten link. the shorten link can be the {serverUrl}/{ramdom text} or {serverUrl}/{alias} 
This first version we didn't save it to database yet.

Project Enviroment
---------------------------
- java 1.8
- Spring boot

Features
--------------------------------------
- Convert the full url into short url and save it
- Check if the full url is valid
- Possibility to create the short link by providing the alias. also check the alias if it's existing otherwise will throw an exception.
- The links will be remove/expired in the minutes that has been provided in application.properties.
- Display all urls

To test
------------------------------------
make sure the server up running
'''
$ mvn clean install
$ java -jar target/PrettyUrl-0.0.1-SNAPSHOT.jar
'''
We have to ways to test
* Postman (Post : generate and save, Get : display List of urls, DELETE : delete the urls base on the time that has been provided)
   - request url :  {serverUrl}/urlInfo
   - Parameters in body(Post) : alias(optional), fullUrl
* Unit test
   - in the unit test we have api testing and service testing








