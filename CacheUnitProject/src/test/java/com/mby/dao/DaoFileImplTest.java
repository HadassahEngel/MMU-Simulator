package com.mby.dao;

import com.mby.dao.DaoFileImpl;
import com.mby.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;
import java.lang.Long;
public class DaoFileImplTest {
    DaoFileImpl<Long> data=null;
    DataModel<Long> model2 = null;
    DataModel<Long> model1 = null;
    DataModel<Long> model3 = null;


    @Test
    public void daoTest() throws Exception {
        DaoFileImpl<String> data=new DaoFileImpl<>("src/main/resources/datasource.json");
        data.clearHradDisk();
        DataModel<String> model2 = new DataModel<>(2L, "page1");
        DataModel<String> model1 = new DataModel<>(1L, "page2");
        DataModel<String> model3 = new DataModel<>(3L, "page3");
        DataModel<String> model4 = new DataModel<>(4L, "page4");
        Assert.assertEquals(null,data.find(2L));
        data.save(model1);
        Assert.assertEquals(null,data.find(2L));
        data.save(model2);
        data.save(model3);
        data.save(model4);
        data.delete(model3);
        Assert.assertEquals(model2 ,data.find(2L));
        Assert.assertEquals(null ,data.find(3L));
        data.save(model4);
        Assert.assertEquals(model4 ,data.find(4L));
    }
}