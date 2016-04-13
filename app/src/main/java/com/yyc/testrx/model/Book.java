package com.yyc.testrx.model;

/**
 * Created by HFF on 16/4/12.
 */
public class Book {
    public boolean back;
    public int id;
    public String name;

    @Override
    public String toString() {
        return "Book{" +
                "back=" + back +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
