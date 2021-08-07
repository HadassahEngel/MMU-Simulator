package com.mby.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mby.dm.DataModel;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerTest {
    @Test
    public void severTest() throws IOException {
        Map<String, String> headerReq = new HashMap<>();
        DataModel<String>[] dataModelObject = new DataModel[]{new DataModel<String>(1L, "aaa"), new DataModel<String>(2L, "bbb")};
        DataModel<String>[] dataModelObject2 = new DataModel[]{new DataModel<String>(5L, "fff"), new DataModel<String>(6L, "qqq"),new DataModel<String>(7L, "zzz")};
        DataModel<String>[] dataModelObject3 = new DataModel[]{new DataModel<String>(7L, "uuf"), new DataModel<String>(8L, "iuq"),new DataModel<String>(9L, "zzz")};
        DataModel<String>[] dataModelObject1 = new DataModel[]{new DataModel<String>(1L, "null"), new DataModel<String>(2L, "null")};
        headerReq.put("action", "UPDATE");
        checkUpdate( headerReq, dataModelObject );
        headerReq.put("action", "GET");
        DataModel<String>[] get=checkGet(headerReq,dataModelObject1);
        Assert.assertArrayEquals(get,dataModelObject);
        headerReq.put("action", "DELETE");
        checkDelete(headerReq,dataModelObject);
        headerReq.put("action", "GET");
        get=checkGet(headerReq,dataModelObject1);
        headerReq.put("action", "UPDATE");
        DataModel<String>[] array=new DataModel[]{null,null};
        checkUpdate( headerReq, dataModelObject );
        checkUpdate( headerReq, dataModelObject2 );
        checkUpdate( headerReq, dataModelObject3 );
        Assert.assertArrayEquals(get,array);
    }


    private void checkDelete(Map<String, String> headerReq,DataModel<String>[] dataModelObject){
        try {
            Gson gson = new Gson();
            Socket myServer = new Socket("127.0.0.1", 12345);
            Request<DataModel<String>[]> req = new Request<>(headerReq, dataModelObject);
            DataOutputStream output;
            DataInputStream input;
            StringBuilder sb = new StringBuilder();
            input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            output = new DataOutputStream(myServer.getOutputStream());
            output.writeUTF(gson.toJson(req));
            output.flush();
            String content = "";
            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);
            content = sb.toString();
            Boolean response=true;
            response = new Gson().fromJson(content, response.getClass());
            Assert.assertTrue(response);
            output.close();
            input.close();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUpdate(Map<String, String> headerReq, DataModel<String>[] dataModelObject
    ){
        try {
            Socket myServer = new Socket("127.0.0.1", 12345);
            DataInputStream input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            DataOutputStream output = new DataOutputStream(myServer.getOutputStream());
            Gson gson = new Gson();
            Request<DataModel<String>[]> req = new Request<>(headerReq, dataModelObject);
            StringBuilder sb = new StringBuilder();
            output.writeUTF(gson.toJson(req));
            output.flush();
            String content = "";
            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);
            content = sb.toString();
            Boolean response=true;
            response = new Gson().fromJson(content, response.getClass());
            Assert.assertTrue(response);
            output.close();
            input.close();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DataModel<String>[] checkGet( Map<String, String> headerReq,DataModel<String>[] dataModelObject) {
        try {
            Socket myServer = new Socket("127.0.0.1", 12345);
            DataInputStream input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            DataOutputStream output = new DataOutputStream(myServer.getOutputStream());
            Request<DataModel<String>[]> req = new Request<>(headerReq, dataModelObject);
            Gson gson = new Gson();
            StringBuilder sb = new StringBuilder();
            output.writeUTF(gson.toJson(req));
            output.flush();
            input = new DataInputStream(new BufferedInputStream(myServer.getInputStream()));
            String content = "";
            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);
            content = sb.toString();
            Type requestType = new TypeToken<DataModel<String>[]>() {
            }.getType();
            DataModel<String>[] response = new Gson().fromJson(content, requestType);
            output.close();
            input.close();
            myServer.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}