package com.mby.server;
import java.lang.Object;
import java.io.Serializable;
import java.util.Map;
import java.lang.String;

/**
 *the class is a patten of the type object that the client sends
 * it has the type a header that a type of a what to do with the pages
 * and a body a array of pages that being taken cared
 * @param <T> array of the type of the pages
 */
public class Request<T>
        extends Object
        implements Serializable{
    private Map<String,String> headers;
    private T body;
    public Request(Map<String, String> headers, T body){
        this.body=body;
        this.headers=headers;
    }

    @Override
    public String toString() {
        return "Request{" +
                "headers=" + headers +
                ", body=" + body +
                '}';
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }


}
