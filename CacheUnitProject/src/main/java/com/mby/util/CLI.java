package com.mby.util;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.lang.Object;
import java.lang.Runnable;
import java.io.OutputStream;
import java.io.InputStream;
import java.beans.PropertyChangeListener;
import java.lang.String;
import java.util.Scanner;



/**
 * the class get from the user if the sever will be started or shutdown
 */
public class CLI extends Object implements Runnable{
    private Scanner in;
    private PrintWriter out;
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);
    private String status = null;
    public  CLI(InputStream in, OutputStream out) {
        this.in=new Scanner(in);
        this.out=new PrintWriter(out);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl){
        this.listener.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl){
        this.listener.removePropertyChangeListener(pcl);
    }

    /**
     * is infinite loop and gets input check if is start
     * then change in the setState if shutdown same as above
     * if not valid write to the user "Not a valid command"
     */
    @Override
    public void run(){
        while(true) {
            write("Please enter your command: [shutdown/start]\n");
            String inputClient=in.next();
            if (inputClient.equals("shutdown")) {
                write("Shutdown Server");
                setStates(inputClient);
            }else if(inputClient.equals("start")){
                write("Starting server.....");
                setStates(inputClient);
            }else{
                write("Not a valid command");
            }
        }
    }
    public String getStates(){
        return this.status;
    }
    public void setStates(String newStates){
        this.status = newStates;
        this.listener.firePropertyChange("status",null, newStates);
    }
    public void	write(String output){
        out.println(output);
        out.flush();
    }
}
