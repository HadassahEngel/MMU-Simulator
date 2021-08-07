package com.mby.services;


import com.mby.dm.DataModel;

import java.io.FileNotFoundException;

/**
 * the class is in charge to controller the sents of the pages to the service
 * @param <T> type of the pages
 */
public class CacheUnitController<T>
            extends Object{
        private CacheUnitService<T> cacheService;

    public CacheUnitController(){
            cacheService=new CacheUnitService<>();
        }
        public boolean	delete(DataModel<T>[] dataModels){
            return cacheService.delete(dataModels);
        }

        public DataModel<T>[] get(DataModel<T>[] dataModels) throws FileNotFoundException {
            return cacheService.get(dataModels);
        }
        public boolean	update(DataModel<T>[] dataModels){
            return cacheService.update(dataModels);
        }

        public void shutdown(){
             cacheService.shutdown();
        }

        public String getStatistics(){return cacheService.getStatistics();}
}
