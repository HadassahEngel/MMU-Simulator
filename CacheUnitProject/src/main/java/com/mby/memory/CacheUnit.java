package com.mby.memory;
import com.hit.algorithm.IAlgoCache;
import com.mby.dm.DataModel;
import java.lang.Long;
import java.lang.Object;
import java.util.Map;

/**
 *
 * @param <T> the type of the content of the pages (that used in the data model
 */
public class CacheUnit <T> extends Object{
    private IAlgoCache<Long, DataModel<T>> algo;


    /**
     *
     * @param algo the type of the algorithm that used to take out pages from the ram
     */
    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo){
        this.algo=algo;
    }

    /**
     *
     * @param ids a array of id of pages in the ram to get there value of the pages
     * @return a array of the value (the pages)
     */
    public DataModel<T>[] getDataModels(Long[] ids){
        int lenOfIdsList=ids.length;
        DataModel<T>[] dataModelsArray;
        dataModelsArray= new DataModel[lenOfIdsList];
        DataModel oneDateModel;
        for (int i=0; i<lenOfIdsList; i++){
            if(algo.getElement(ids[i])!=null){
                oneDateModel=algo.getElement(ids[i]);
                dataModelsArray[i]=oneDateModel;
            }
            else {
                dataModelsArray[i]=null;
            }
        }
        return dataModelsArray;
    }

    /**
     *
     * @param datamodels a array of page to put in the ram
     * @return return array of pages or that puren in the ram of that taken out of the ram
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels){
        int lenOfDatamodels=datamodels.length;
        DataModel<T>[] dataModelsArray;
        dataModelsArray= new DataModel[lenOfDatamodels];
        DataModel oneDateModel;
        for (int i=0; i<lenOfDatamodels;i++){
           oneDateModel=algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
           if(oneDateModel!=null){
               dataModelsArray[i]=oneDateModel;
           }
        }
        return dataModelsArray;
    }

    /**
     *
     * @param ids a array of ids ib of pages in the ram ane delete the pages from the ram
     */
    public  void removeDataModels(Long[] ids){
        for (int i=0; i<ids.length; i++){
            algo.removeElement(ids[i]);
        }
    }



    public DataModel<T>[] getAllRam() {
        Map<Long, DataModel<T>> map=algo.getPageValue();
        DataModel<T>[] allRam=new DataModel[map.size()];
        int i=0;
        for (Map.Entry<Long, DataModel<T>> entry : map.entrySet()) {
            allRam[i]=entry.getValue();
            i++;
        }
        return allRam;
    }
}

