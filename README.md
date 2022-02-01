# POO

HOW TO USE THE APP

- Download chatApp.jar, and save it in a folder where you have permissions
- Change it's properties so that it is executable, for example using the following command: 
  chmod 777 chatApp.jar
- Run the file using OpenJDK java 11 Runtime and the application will be opened. 
- From there, you can choose a pseudo for other connected users to recognise you by, see who are connected, 
  receive conversation requests and start conversations. 
 
 
REQUIREMENTS FOR THIS APPLICATION

In order to communicate with other users through this application, you should be connected to the same physical network.

It is required that the users of this chat application have their own proper computers that they connect from every time. 
By running the application from another computer than your own, you will be considered as a new user by the system, 
  and you will not be able to access your previous chats and conversations. It is also required that the computers on the 
  network do not change IP addresses.
  
  
RUNNING THE APP FROM THE SOURCE CODE

- Open the project in Eclipse for Java Developers
- Import the project to your workspace as an existing maven project
- Go to pooProjectVFnerea/src/main/java/gui/Login.java
- Run Login.java as java application

NB! At the moment the application uses a database only accessible from the GEI network at INSA, and storing or reading previous
messages will only be possible when connected to this network. 
