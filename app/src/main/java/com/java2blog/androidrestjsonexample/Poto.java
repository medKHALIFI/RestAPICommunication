package com.java2blog.androidrestjsonexample;

public class Poto {
    int id;
    String potoName;

    public Poto(int i, String potoName) {
        super();
        this.id = i;
        this.potoName = potoName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPotoName() {
        return potoName;
    }
    public void setPotoName(String potoName) {
        this.potoName = potoName;
    }
}
