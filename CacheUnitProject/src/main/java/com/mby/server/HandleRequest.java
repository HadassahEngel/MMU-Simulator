package com.mby.server;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mby.dm.DataModel;
import com.mby.services.CacheUnitController;
import java.lang.Object;
import java.lang.Runnable;
import java.util.Map;

/**
 * the class in charge to get form the client
 * @param <T>  the type of the pages
 */
public class HandleRequest<T>
        extends Object
        implements Runnable {

    private Socket socket;
    private CacheUnitController<T> controller;
    /**
     * a map to put the type of the header of request that will get from the client
     */
    private Map<String, String> header;

    /**
     *
     * @param s socket that the get form the sever
     * @param controller the controller that will send the array to the data model to put in
     */
    public HandleRequest(Socket s, CacheUnitController<T> controller){
        this.socket=s;
        this.controller=controller;
    }

    /**
     * the function run starts wen will be a new thread that type of handle request
     * (wen a new client connect to to server)
     * the method reads from the client a object  type request
     * check the header
     * and sends to the controller the array of the data model to the right function according to the type of the header
     */
    public void run(){
        try {
            Gson gson = new Gson();
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";
            do {
                content = in.readUTF();
                sb.append(content);
            } while (in.available() != 0);
            content = sb.toString();
            if (content.equals("Statistics")) {
                String response=controller.getStatistics();
                DataOutputStream output;
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(response);
            } else {
                Type requestType = new TypeToken<Request<DataModel<T>[]>>() {
                }.getType();
                Request<DataModel<T>[]> request = new Gson().fromJson(content, requestType);
                header = request.getHeaders();
                boolean success = false;
                switch (header.get("action")) {
                    case "GET": {
                        try {
                            DataModel<T>[] dataModel = new DataModel[request.getBody().length];
                            dataModel = controller.get(request.getBody());
                            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                            writer.writeUTF(gson.toJson(dataModel));
                            writer.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                    case "DELETE": {
                        try {
                            success = controller.delete(request.getBody());
                            DataOutputStream output;
                            output = new DataOutputStream(socket.getOutputStream());
                            output.writeUTF(gson.toJson(success));
                            output.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                    case "UPDATE": {
                        try {
                            success = controller.update(request.getBody());
                            DataOutputStream output;
                            output = new DataOutputStream(socket.getOutputStream());
                            output.writeUTF(gson.toJson(success));
                            output.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
