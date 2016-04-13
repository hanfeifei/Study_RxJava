# RxJava (Reactive Extensions for the JVM)

### 简介

**a library for composing asynchronous and event-based programs by using observable sequences.**

**项目主页:** 
>[https://github.com/ReactiveX/RxJava](https://github.com/ReactiveX/RxJava)
	
**特点：**
> * 易于并发从而更好的利用服务器的能力
> * 易于有条件的异步执行
> * 一种更好的方式来避免回调地狱
> * 一种响应式方法

**四种角色：**
> * Observable
> * Observer
> * Subscriber
> * Subjects 

### 观察者模式
![img](http://ww3.sinaimg.cn/mw1024/52eb2279jw1f2rx4ay0hrg20ig08wk4q.gif)

### 使用
**1.创建Observable（被观察者，事件的产出者）**

```
**方法1**

Observable observable = Observable.create(new Observable.OnSubscribe<String>() { 
    @Override
    public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("呵呵1");
        subscriber.onNext("呵呵2");
        subscriber.onNext("呵呵3");
        subscriber.onCompleted();
    }
});
```
```
**方法2**

Observable observable = Observable.just("呵呵1", "呵呵2", "呵呵3");
```
```
**方法3**

String[] strArray = {"呵呵1", "呵呵2", "呵呵3"};
Observable observable = Observable.from(strArray);
```

**2.Subscribe (订阅)**

```
observable.subscribe(observer);
或者：
observable.subscribe(subscriber);
```
**3. Observer处理事件**

````
new Observer<String>() {
    @Override
    public void onNext(String str) {
    	Log.d("---Observer--",str);
            }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show();
    }
}
````
**4.变换(map(),flatMap())**

```
**map()**

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
```

```
**flatMap()**

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
```

**5.线程控制Scheduler**

 * 线程调度方法：
 * subscribeOn()——事件产生的线程
 * observeOn()——事件消费的线程

> * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。

> * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
> * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
> * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
> * Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。

### 适用场景

 **1.与 Retrofit 的结合**
 
 [项目主页](https://github.com/square/retrofit)
 
**3.各种异步操作**

**4.使用RxJava实现各种Rx**



