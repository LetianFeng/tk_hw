== Build ==
build.xml is provided in the root folder, use ant to build the project.

== Execution ==
Running Ant script will spawn 2 micro blog clients.
The basic setup for ActiveMQ is 5.6.0 running on tcp://localhost:61616. This url can be set using a command line argument. (Eg. java microblog.jar -Dhost="tcp://192.168.1.100:61616")

== Blog User Manual ==
1 Hashtags are used at the beginning and the end of text to specify a tag (e.g #tag#).
2 #PULIC# tag is subscribed for each client as default, this functions as a mean to discover other users and topics.
3 Subscibe option will pop up when clicking on a tag, profile pictue or user name.
4. Message received will trigger notification at top, clicking on refresh button on the top right corner will render the blog posts in GUI.
5. Centralized managment module for tags and users are accessible through the cogwheel icon on the top right corner.
