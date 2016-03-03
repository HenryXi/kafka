package com.henry.xi.kafka;

import java.io.Serializable;

public class Message implements Serializable {
    public Message(){}
    public Message(String id, String body) {
        this.id = id;
        this.body = body;
    }

    private String id;
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
