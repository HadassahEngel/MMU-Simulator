package com.mby.server;
import com.mby.services.CacheUnitController;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.Object;
import java.beans.PropertyChangeListener;
import java.lang.Runnable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * the class is a sever that listens to the CLI
 *  to see if the it on or off  (start/ shutdown) and get the socket form the client and sends it to the HandleRequest
 */
public class Server
        extends Object
        implements PropertyChangeListener, Runnable{
    private ServerSocket socket;
    private CacheUnitController<String> controller;
    static ExecutorService service;
    private boolean serverUp;
    private boolean isAlive;

    public Server(){
        socket = null;
        controller =  new CacheUnitController<String>();
        serverUp = false;
        isAlive=false;
    }

    /**
     * checks if the new event is start and its was stop so change the boolean serverUp and start new thread
     * if the event is shutdown if was start before change the boolean serverUp
     * and then one thread can run and then the thread wall close (the this thread)
     * @param evt the new state that the client entered
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        String action =(String) evt.getNewValue();
        if(action.equals("start")){
            if(!serverUp){
                serverUp=true;
                if(!isAlive)
                {
                new Thread(this).start();
                }
            }
        }else if(action.equals("shutdown")) {
            if (serverUp) {
                serverUp = false;
            }
        }
    }



    /**
     * run wen the thread start and if the serverUp is true opens a new thread otherwise open a
     * close the teared
     */
    public void run(){
        isAlive=true;
        service = Executors.newCachedThreadPool();
        try {
            socket = new ServerSocket(12345);
            while (serverUp) {
                Socket someClient = socket.accept();
                service.execute(new HandleRequest<String>(someClient,controller));
            }
            controller.shutdown();
            socket.close();
            isAlive=false;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(socket != null)
                    socket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}