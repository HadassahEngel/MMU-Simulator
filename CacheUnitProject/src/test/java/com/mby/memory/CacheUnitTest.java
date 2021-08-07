package com.mby.memory;

import com.hit.algorithm.MFUAlgoCacheImpl;
import com.mby.dm.DataModel;
import com.mby.memory.CacheUnit;
import org.junit.Assert;
import org.junit.Test;
import java.lang.Long;
public class CacheUnitTest{

    @Test
    public void cacheTest(){
        MFUAlgoCacheImpl<Long,DataModel<Long>> mfu=new MFUAlgoCacheImpl<>(3);
        CacheUnit<Long> cache=new CacheUnit<>(mfu);
        DataModel<Long> dataModel1=new DataModel<>(1L, 1L);
        DataModel<Long> dataModel2=new DataModel<>(2L, 2L);
        DataModel<Long> dataModel3=new DataModel<>(3L, 3L);
        DataModel<Long> dataModel4=new DataModel<>(4L, 4L);
        DataModel<Long>  [] arrayDataModel=new DataModel[3];
        DataModel<Long> [] arrayDataModel2;
        arrayDataModel[0]=dataModel1;
        arrayDataModel[1]=dataModel2;
        arrayDataModel[2]=dataModel3;
        arrayDataModel2=cache.putDataModels(arrayDataModel);
        for (DataModel<Long> longDataModel : arrayDataModel2) {
            Assert.assertNull(longDataModel);
        }
        Long[] longArray=new Long[3];
        longArray[0]=dataModel1.getDataModelId();
        longArray[1]=dataModel2.getDataModelId();
        longArray[2]=dataModel3.getDataModelId();
        arrayDataModel2=cache.getDataModels(longArray);
        for(int i=0;i<arrayDataModel2.length;i++){
            Assert.assertEquals(arrayDataModel[i],arrayDataModel2[i]);
        }
        longArray[1]=dataModel1.getDataModelId();
        arrayDataModel2=cache.getDataModels(longArray);
        for(int i=0;i<arrayDataModel2.length;i++){
            if(i==1){
                Assert.assertEquals(arrayDataModel[0],arrayDataModel2[i]);
            }
            else {
                Assert.assertEquals(arrayDataModel[i], arrayDataModel2[i]);
            }
        }
        DataModel<Long> [] arrayDataModel3=new DataModel[1];
        arrayDataModel3[0]=dataModel4;
        arrayDataModel3=cache.putDataModels(arrayDataModel3);
        Assert.assertEquals(dataModel1,arrayDataModel3[0]);
        Long[] longArray2=new Long[2];
        longArray2[0]= 2L;
        longArray2[1]= 3L;
        cache.removeDataModels(longArray2);
        arrayDataModel[0]=dataModel4;
        longArray[0]=dataModel4.getDataModelId();
        arrayDataModel2=cache.getDataModels(longArray);
        for(int i=0;i<arrayDataModel2.length;i++){
            if(i==0){
                Assert.assertEquals(arrayDataModel[0],arrayDataModel2[i]);
            }
            else{
                Assert.assertNull(arrayDataModel2[i]);
            }
        }
    }

}