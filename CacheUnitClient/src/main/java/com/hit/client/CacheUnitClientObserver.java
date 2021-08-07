package com.hit.client;
import com.hit.view.CacheUnitView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;

/**
 * listens to interface changes from user (client)
 * and send the changes the request to the CacheUnitClient
 */
public class CacheUnitClientObserver implements PropertyChangeListener, EventListener {
    private CacheUnitClient cacheUnitClient;
    private CacheUnitView cacheUnitView;

    public CacheUnitClientObserver(){
        cacheUnitClient=new CacheUnitClient();
    }

    /**
     * send to CacheUnitClient the request and send the response that get back to cacheUnitView
     * @param evt the change in the user interface
     */
    @Override
    public void	propertyChange(PropertyChangeEvent evt) {
        String response;
        response = cacheUnitClient.send((String) evt.getNewValue());
        cacheUnitView = (CacheUnitView) evt.getSource();
        cacheUnitView.updateUIData(response);
    }
}
