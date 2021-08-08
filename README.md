# MMU-Simulator

System that simulates the memory management unit of the computer,

That can use different paging algorithms (MFU/Random/LRU). The project is written in JAVA.

### The project has two parts

1. Server side (simulates the MMU)-its in charge of getting request form the client and according to the request puts/gets/update the pages in the RAM.

2. Client side (simulates the OS)- send the server a request of which pages to puts/gets/update and gets a response back.

### Run the project:
Open the client and server side each in a different window
#### Run the server side:
*	Go to the com.hit.mby.server and run CacheUnitDriver.
* Typing start in the command line.
#### Run the client side:
* Go to the com.hit.mby.driver and run CacheUnitClientDriver
* Now you can send requests using the GUI.
