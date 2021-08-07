package com.mby.services;

import com.mby.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class CacheUnitServiceTest{
    @Test
    public void checkService() throws FileNotFoundException {
        CacheUnitService<String> service=new CacheUnitService<>();
        DataModel<String>[] dataModelObject1 = new DataModel[]{new DataModel<String>(1L, "aaa"),
                new DataModel<String>(2L, "bbb")};
        DataModel<String>[] dataModelObject2 = new DataModel[]{new DataModel<String>(3L, "ccc"),
                new DataModel<String>(4L, "ddd"), new DataModel<String>(5L, "eee")};
        DataModel<String>[] dataModelObject3 = new DataModel[]{new DataModel<String>(3L, "fff"),
                new DataModel<String>(1L, "bbb"), new DataModel<String>(5L, "eee")};
        DataModel<String>[] dataModelObject4 = new DataModel[]{new DataModel<String>(1L, null),
                new DataModel<String>(2L, null)};
        DataModel<String>[] dataModelObject5 = new DataModel[]{new DataModel<String>(1L, "bbb"),
                new DataModel<String>(2L, "bbb")};
        DataModel<String>[] dataModelFromGet=new DataModel[2];
        boolean result= service.delete(dataModelObject1);
        Assert.assertTrue(result);
        result= service.update(dataModelObject1);
        Assert.assertTrue(result);
        dataModelFromGet=service.get(dataModelObject4);
        Assert.assertArrayEquals(dataModelFromGet,dataModelObject1);
        result= service.update(dataModelObject2);
        Assert.assertTrue(result);
        result= service.update(dataModelObject3);
        Assert.assertTrue(result);
        dataModelFromGet=service.get(dataModelObject4);
        Assert.assertArrayEquals(dataModelFromGet,dataModelObject5);
        result= service.delete(dataModelObject5);
        Assert.assertTrue(result);
        service.shutdown();
    }
}