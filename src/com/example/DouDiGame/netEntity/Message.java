package com.example.DouDiGame.netEntity;

/**
 * Created by viv on 16-4-26.
 */
public class Message {

    /*标记信息用途*/
    String forWhat;
    /*标记做什么/或者状态*/
    String doSomething;
    /*信息承载*/
    String data;


    public String getDoSomething() {
        return doSomething;
    }

    public void setDoSomething(String doSomething) {
        this.doSomething = doSomething;
    }

    public String getForWhat() {
        return forWhat;
    }

    public void setForWhat(String forWhat) {
        this.forWhat = forWhat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                ", forWhat='" + forWhat + '\'' +
                ", doSomething='" + doSomething + '\'' +
                '}';
    }
}
