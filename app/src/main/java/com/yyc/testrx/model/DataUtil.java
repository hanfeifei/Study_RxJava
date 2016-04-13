package com.yyc.testrx.model;

import java.util.ArrayList;

/**
 * Created by HFF on 16/4/12.
 */
public class DataUtil {

    public static ArrayList<Employee> getData() {
        ArrayList<Employee> list = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.id = i;
            book.name = "换花洗剑录" + i;
            if (i % 2 == 0) {
                book.back = true;
            } else {
                book.back = false;
            }
            books.add(book);
        }
        for (int j = 0; j < 5; j++) {
            Employee em = new Employee();
            em.books = books;
            em.id = j + 20;
            em.name = "员工" + (j + 20);
            list.add(em);
        }
        return list;
    }
}
