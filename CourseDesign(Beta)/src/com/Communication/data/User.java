package com.Communication.data;

public class User {
    private static String name,pwd;
    public User() {
        super();
    }
    public User(String name, String pwd) {
        super();
        this.name = name;
        this.pwd = pwd;
    }
    public static String getName() {
        return name;
    }
    public  void setName(String name) {
        this.name = name;
    }
    public static String getPwd() {
        return pwd;
    }
    public  void setPwd(String pwd) {
        this.pwd = pwd;
    }
}