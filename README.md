# mmu-simulator

system that simulates the memory management unit of the computer,

that can use different paging algorithms (MFU/Random/LRU). The project is written in JAVA.

### The project has two parts

1. server side (simulates the MMU)-its in charge of getting request form the client and according to the request puts/gets/update the pages in the RAM.

2. client side (simulates the OS)- send the server a request of which pages to puts/gets/update and gets a response back.

### Run the project:
Open the client and server side each in a different window
#### Run the server side:
*	go to the com.hit.mby.server and run CacheUnitDriver.
* typing start in the command line.
#### Run the client side:
* go to the com.hit.mby.driver and run CacheUnitClientDriver
* now you can send requests using the GUI.
