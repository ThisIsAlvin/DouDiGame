package com.example.DouDiGame.model;

/**
 * Created by viv on 16-4-26.
 */
public class Message {

    String foWhat;
    String doSomething;

    public String getDoSomething() {
        return doSomething;
    }

    public void setDoSomething(String doSomething) {
        this.doSomething = doSomething;
    }

    public String getFoWhat() {
        return foWhat;
    }

    public void setFoWhat(String foWhat) {
        this.foWhat = foWhat;
    }

    @Override
    public String
    toString() {
        return "Message{" +
                "doSomething='" + doSomething + '\'' +
                ", foWhat='" + foWhat + '\'' +
                '}';
    }
}
