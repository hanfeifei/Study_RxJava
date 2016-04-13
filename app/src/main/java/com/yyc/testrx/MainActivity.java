package com.yyc.testrx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yyc.testrx.model.Book;
import com.yyc.testrx.model.DataUtil;
import com.yyc.testrx.model.Employee;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

    ArrayList<Employee> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = DataUtil.getData();
        test7();
    }


    /**
     * 测试基本的打印字符串功能——使用create方式
     */
    private void test1() {

        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("呵呵1");
                subscriber.onNext("呵呵2");
                subscriber.onNext("呵呵3");
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Log.d("----test1----", s);
            }
        });
    }

    /**
     * 测试基本的打印字符串功能——使用just方式
     */
    private void test2() {

        Observable.just("呵呵1", "呵呵2", "呵呵3").subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Log.d("----test2----", s);
            }
        });
    }

    /**
     * 测试基本的打印字符串功能——使用form方式
     */
    private void test3() {

        String[] strArray = {"呵呵1", "呵呵2", "呵呵3"};
        Observable.from(strArray).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Log.d("----test3----", s);
            }
        });
    }

    /**
     * 循环嵌套代码演示
     */
    private void test4() {
        for (Employee em : list) {
            for (Book book : em.books) {
                if (book.back) {
                    Log.d("---book---", em.name + "::" + book.toString());
                }
            }
        }
    }

    /**
     * 测试单变换——map()
     */
    private void test5() {


        Observable.from(list).map(new Func1<Employee, Employee>() {
            @Override
            public Employee call(Employee employee) {
                return employee;
            }
        }).subscribe(new Observer<Employee>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Employee e) {
                Log.d("---test4---", e.toString());
            }
        });
    }

    /**
     * 测试单变换——flatMap()
     */
    private void test6() {


        Observable.from(list).flatMap(new Func1<Employee, Observable<Book>>() {

            @Override
            public Observable<Book> call(Employee employee) {
                return Observable.from(employee.books);
            }
        }).filter(new Func1<Book, Boolean>() {
            @Override
            public Boolean call(Book book) {
                return book.back;
            }
        }).subscribe(new Observer<Book>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Book book) {
                Log.d("---test6---", book.toString());
            }
        });
    }

    /**
     * 线程控制
     */
    private void test7() {
        Observable.create(new Observable.OnSubscribe<Employee>() {

            @Override
            public void call(Subscriber<? super Employee> subscriber) {
                //从数据库拿数据
                //抛出去
                subscriber.onNext(new Employee());
                subscriber.onNext(new Employee());
                subscriber.onNext(new Employee());
                subscriber.onCompleted();

                Log.d("----OnSubscribe----",Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.newThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                //做准备工作,显示progressbar
                Log.d("----doOnSubscribe----",Thread.currentThread().getName());

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Employee>() {
            @Override
            public void onCompleted() {
                //显示,放到列表中
                Log.d("----onCompleted----",Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Employee employee) {
                //将数据放入list中
                list.add(employee);
                Log.d("----onNext----",Thread.currentThread().getName());

            }
        });
    }
}
