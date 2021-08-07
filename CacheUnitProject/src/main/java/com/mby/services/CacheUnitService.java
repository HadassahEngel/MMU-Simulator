package com.mby.services;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.mby.dao.DaoFileImpl;
import com.mby.dao.IDao;
import com.mby.dm.DataModel;
import com.mby.memory.CacheUnit;
import java.io.FileNotFoundException;
import java.lang.Object;
import java.util.Map;

/**
 *the class is in charge to taken cared of the pages that the client send
 * in the ram and hard disk
 * @param <T> the type of the pages
 */
public class CacheUnitService<T>
        extends Object {
    private IDao<Long, DataModel<T>> dao;
    private CacheUnit<T> cache;
    private String chosedAlgorithm;
    private int sizeCapacity;
    static Map<String,Integer> mapStatistics;
    static int totalNumRequest;
    static int totalNumDataModelSwaps;
    static int totalNumDataModel;

    static{
        totalNumRequest=0;
        totalNumDataModelSwaps=0;
        totalNumDataModel=0;
    }


    public CacheUnitService(){
        this.chosedAlgorithm = "LRU";
        this.sizeCapacity = 4;
        this.cache = new CacheUnit<T>(new LRUAlgoCacheImpl<Long, DataModel<T>>(this.sizeCapacity));
        this.dao = new DaoFileImpl<>("src/main/resources/datasource.json");
    }


    public int getSizeCapacity() {
        return sizeCapacity;
    }

    public String getChosedAlgorithm() {
        return chosedAlgorithm;
    }


    /**
     * delete all the the pages that gets from the ram and hard disk
     * @param dataModels the array page that need to be deleted
     * @return true if succeeded otherwise returns false
     */
    public boolean	delete(DataModel<T>[] dataModels){
        totalNumRequest++;
        totalNumDataModel+=dataModels.length;
        try {
            Long ids[] = new Long[dataModels.length];

            for (int i = 0; i < dataModels.length; i++) {
                dao.delete(dataModels[i]);
            }
            for (int i = 0; i < ids.length; i++) {
                ids[i] = dataModels[i].getDataModelId();
            }
            cache.removeDataModels(ids);
        }catch(Exception ex) {
            return false;
        }
        return true;
    }

    /**
     *  checks the value form the ram
     *  if don't exist get the value from the ram and puts that page in ram
     * @param dataModels the array page that need to get there value
     * @return the pages with the values in the memory
     * @throws FileNotFoundException
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels) throws FileNotFoundException {
        totalNumRequest++;
        totalNumDataModel+=dataModels.length;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            ids[i] = dataModels[i].getDataModelId();
        }
        DataModel<T>[] tempDM = new DataModel[dataModels.length];
        tempDM = (DataModel<T>[]) cache.getDataModels(ids);

        for (int i = 0; i < dataModels.length; i++) {
            if (tempDM[i] == null)
                tempDM[i] = (DataModel<T>) dao.find(dataModels[i].getDataModelId());
        }
        return tempDM;
    }

    /**
     *  puts the pages in ram (if exist in ram update them otherwise put in ram)
     *  if the ram is full update in hard disk the pages that removed from the ram according to the algorithm
     * @param dataModels the array page that need to update them
     * @return true if succeeded otherwise returns false
     */
    public boolean	update(DataModel<T>[] dataModels){
        totalNumRequest++;
        totalNumDataModel+=dataModels.length;
        DataModel<T>[] tempDM = cache.putDataModels(dataModels);
        for (int i = 0; i < dataModels.length; i++) {
            if (tempDM[i] != null) {//??????????????.
                totalNumDataModelSwaps++;
                try {
                    dao.save(tempDM[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * gets all the pages that's in the ram and update them in the hard disk
     */
    public String getStatistics(){
        String statistics="Capacity: "+ sizeCapacity+ "\n"+"Algorithm: "+chosedAlgorithm+"\n"+"total num of request:  "+ totalNumRequest +"\n"
                +"total num od dataModel (GET/DELETE/UPDATE requests):  "+totalNumDataModel +"\ntotal num of DataModel swaps(from Cache to Disk):  "+totalNumDataModelSwaps;
        return statistics;
    }

    public void shutdown(){
        DataModel<T>[] allRam=cache.getAllRam();
        for (int i = 0; i < allRam.length; i++) {
                try {
                    dao.save(allRam[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

    }
}
