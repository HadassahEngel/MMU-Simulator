package com.hit.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.String;
import java.net.Socket;

/**
 * the class sends to the server the request  that the client asked and gets back the answer from the server
 */
public class CacheUnitClient {
    /**
     * socket-that to send and gets from the server
     * input-the input that gets from the server
     * output-the output that sends to the server
     */
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    public CacheUnitClient(){
    }

    /**
     *
     * @param request the string that the client wants to send the server
     *                (For example, check the statistics, Update Pages in to the RAM)
     * @return return the response that the server sends
     */
    public String send(String request) {
        String response = null;
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            output.writeUTF(request);	//write to server
            output.flush();
            response = (String) input.readUTF();
            if(response.equals("false")){
                response="Failed";
            }else if(response.equals("true")){
                response="Succeeded ";
            }
            socket.close();
            output.close();
            input.close();
            return response;
        }catch (IOException e){
            return "can't connect to sever";
        }
    }

}
