package com.hit.view;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.*;

/**
 *the class that has the view to the client and listeners to the change that the user does
 */
public class CacheUnitView {
    /**
     *listener-the listener
     * frame-the frame of the view s
     * panel-the screen thet the user all see
     */
    private PropertyChangeSupport listener;
    private JFrame frame;
    private CacheUnitPanel panel;


    public CacheUnitView(){
        listener = new PropertyChangeSupport(this);
        panel = new CacheUnitPanel(listener);
        frame = new JFrame("CacheUnitUI");
    }

    /**
     *
     * @param pcl The class that wants to listen to this class
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl){
        this.listener.addPropertyChangeListener(pcl);
    }

    /**
     *
     * @param pcl The class that wants to stop listen to this class
     */
    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl){
        this.listener.removePropertyChangeListener(pcl);
    }

    /**
     *
     * @param t the data
     * @param <T> the type of data that get from the server (gets from CacheUnitClientObserver)
     */
    public <T> void updateUIData(T t){
        panel.textArea.setText((String) t);
    }

    /**
     * set the panel of the screen and start to run it
     */
    public void start(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.add(panel.getPanel());
                frame.setSize(600, 500);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }
}
