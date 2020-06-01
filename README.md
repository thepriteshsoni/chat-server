# Websockets-Based Chat Server
A CLI internet chat interface that accommodates text messages as well as Linux/ shell commands!

Chat messages and system commands are differentiated using the prefix pattern: "//"

i. Messages that don't start with "//" are simple text messages that will be broadcasted to every client subscribed to the server.

ii. Messages that start with "//" will be considered sustem commands and will be executed as-is.
The result of the command will be appended along with the command and will have a suffix "ACK" appended to it in case of a successful execution, otherwise, the suffix "NOACK" will be appended.

# Prerequisites
1. Java - 1.8.x
2. Maven - 3.x.x

# How to run?
1. Clone the application:
git clone https://github.com/thepriteshsoni/chat-server.git

2. Go to application.properties in the resources directory.

3. Change the property value of "messages.file.location" to the absolute directory path of your system where the repo is cloned. Ensure that the value ends with the exact location of messages.txt file present inside the resources directory in the repo e.g. /Users/Pritesh/PKS/code/pks-programming/chat-server/src/main/resources/messages.txt

4. Run the app using maven:

a. If you already have a pre-existing project-specific settings.xml file for Maven with a remote repository, use the one provided along with the repo:
mvn spring-boot:run -s <Path to your working directory>/chat-server/settings.xml

b. If you don't have a project-specific settings.xml file, run the following maven command to execute the project:
mvn spring-boot:run

# Contact the developer
For queries and clarifications, kindly drop in a mail to thepriteshsoni@gmail.com
