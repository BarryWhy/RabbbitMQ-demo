package com.rabbitmq.model;

import java.io.Serializable;

/**
 * Created by Barry
 * 2018/10/15 15:32
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -9083840843622181198L;
    private String id;
    private String name;
    private String messageId;

    public Order() {
    }

    public Order(String id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
