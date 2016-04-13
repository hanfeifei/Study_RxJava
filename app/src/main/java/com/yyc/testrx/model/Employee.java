package com.yyc.testrx.model;

import java.util.ArrayList;

/**
 * Created by HFF on 16/4/12.
 */
public class Employee {
    public int id;
    public String name;
    public ArrayList<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
