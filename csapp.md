文件类型：
普通文件：二进制和文本文件，文本文件就是unicode和ascii码，二进制如音屏，视频
目录：就是包含一组链接的文件
套接字：网络传输使用

还有一个特殊的文件：管道，用来连接两个进程

linux的文件就是一个m字节的序列

操作系统每打开一个文件，内核就会返回一个小的非负整数，叫做描述符，按顺序编号

linux shell创建的每个进程开始都有三个打开的文件：标准输入（描述符0），标准输出（描述符1）和标准错误（描述符2）

windows和linux的操作系统对于文本的换行符不一样：
linux和mac：是/n
windows: 是 /r/n

c语言中open close read write函数来操作文件（详细见TCP/ip编程）

概述：
进程：本质上是一个正在执行的一个程序，与每个进程相关的是地址空间，地址空间是从某个最小的储存位置到某个最大的储存位置列表

进程表：除了进程自身地址空间的内容以外，与进程所有有关信息的表，是数组或链表结构，方便进程挂起后记录运行的状态，方便恢复。

进程间通信：若一个进程能够创建一个或者多个子进程，合作完成相关作业必须要相互通信

每个进程都有个UID

Shell：操作系统进行系统调用的代码。编辑器、编译器、汇编程序、链接程序、效用程序以及命令解释器等，尽管非常重要，也非常有用，但是他们确实不是操作系统的组成部分。

**系统调用：
**
用户调用系统调用函数时，会执行一个trap指令，把系统从用户状态切换到内核状态，并在内核的一个固定地址开始执行。耗费系统资源
常见的系统调用函数有以下：
![trap](pic/trap.jpg)

在unix中只有fork可以创建进程，子进程相当于创建了父进程的副本，拥有自己独立的的数据结构。

unix的进程将其储存空间划分为三段：正文段（如程序代码），数据段（如变量）、堆栈段

在unix中，每个文件都有一个保护模式。该模式包括针对所有者、组和其他用户的读-写-执行位。chmod系统调用可以改变文件的模式。例如要使一个文件对除了所有者之外的用户只读，可以执行：
chmod("file"，0644)

windows和unix不一样，unix系统调用和系统调用所使用的库之间几乎是一一对应的关系。换句话说每个系统调用就涉及一个被调用库的过程
而windows是提供了win32应用编程接口，程序员用这套过程获得操作系统服务。


# 二、进程与线程

![processandthread](pic/process-and-thread.png)

进程是对正在运行程序的一个抽象。
在进程模型中，计算机上所有可运行的软件，通常包括操作系统，被组织成若干顺序进程，简称进程。

进程是资源分配的基本单位，用来管理资源（例如：内存，文件，网络等资源）

进程控制块 (Process Control Block, PCB) 描述进程的基本信息和运行状态，所谓的创建进程和撤销进程，都是指对 PCB 的操作。（PCB是描述进程的数据结构）

进程的四种主要创建方式：
1. 系统初始化
2. 正在运行的程序执行了创建进程的系统调用
3. 用户请求创建一个新进程
4. 一个批处理作业的初始化

启动操作系统时，通常会创建若干个进程。其中有些时前台进程（用户交互），后台进程，以及守护进程。

进程的三种状态：
1. 运行态（该时刻进程实际占用CPU）
2. 就绪态（可运行，但因为其他进程正在运行而暂时停止）
3. 阻塞态（除非某种外部事件发生，否则进程不能运行，例如read等待tcp传输信息）
![pcondition](pic/pcondition.jpg)

进程的实现：
操作系统维护着一张表格，即进程表。每个进程占用一个进程表项。
该表项包含了进程状态的重要信息，包括程序计数器、堆栈指针、内存分配状况、所打开文件状态、调度信息，以及上下文切换后和被阻塞后的状态信息保存。从而保证该进程随后能够再次启动，就像从未被中断过一样。

多道程序设计模型：
例如：假设进程用于计算的平均时间是进程在内存中停留时间的20%，那么同时运行5个进程，则CPU将一直满负荷运行。
但是这种假设过于乐观，因为它假设这5个进程不会同时等待I/O.

假设一个进程等待I/O操作的时间与其停留在内存中的时间比为p，则当内存中同时有n个进程时。所有n个进程都在等待IO（此时cpu空转）的概率时p^n。
CPU利用率 = 1 - p^n

## 2.2线程
线程是独立调度的基本单位。

一个进程中可以有多个线程，它们共享进程资源。

QQ 和浏览器是两个进程，浏览器进程里面有很多线程，例如 HTTP 请求线程、事件响应线程、渲染线程等等，线程的并发执行使得在浏览器中点击一个新链接从而发起 HTTP 请求时，浏览器还可以响应用户的其它事件。

线程的优点:
1. 线程比进程更轻量级，更容易创建也更容易撤销。创建一个线程比一个进程要快10~100倍。
2. 多线程处理大量计算和IO处理会更快


## 2.3 进程间的通信

临界区：对共享内存进行访问的程序片段

互斥方案：
1. 屏蔽中断：有进程进入临界区后，屏蔽其他进程进入，直到该进程完成。缺点：该屏蔽只能屏蔽该cpu的进程，多核处理器其他核心的进程也可以访问共享内存
2. 锁变量：设置一个共享（锁）变量，其初始值为0，进程进入后设置为1，当其他进程准备进入读取锁值为1，则阻塞，直到为0后在进入。（0表示临界区没有进程）缺点：如果另一个进程在共享变量修改为1之前就进入了临界区，那么临界区还是会有两个程序。（有人说那么一个程序在修改锁变量为1时再检查一次，这样也不行，因为锁变量是在第二次检查前修改为1还是会出错）

生产者和消费者的问题：
适用于一个生产者和一个消费者的解法，不适合多个
其中有一个很大的问题：count这个共享整型，没有进行隔离操作。会有读取丢失的问题
```c
#define N  100   //缓冲区的
int count = 0;

void produce(void){
	int item;
	while(TRUE){
		item = produce_item();//放在最前的原因，先生产一个放在这，如果能加进去就加，加不进去就放着阻塞，等能加进去为止
		if(count == N) sleep();//如果仓库是满的，阻塞
		insert_item(item);//增加项目
		count = count + 1;
		if(count == 1) wakeup(consumer);//如果count = 1，那么之前它肯定等于0，所以消费者肯定再等待，所以唤醒消费者。
	}
}

void consumer(void){
		int item;
	while(TRUE){
		if(count == 0) sleep();
		item = remove_item();
		count = count - 1;
		if(count == N-1) wakeup(producer);
		consume_item(item);//放在最后的原因，先看能否取出，如能取出则消费，如不能，则到不了这一步
	}
}
```

用java代码还原了丢失问题：
```java
import java.util.ArrayList;
public class testMultiThread {
    public static int N = 5;//仓库容量
    public static int count = 0;//仓库物品计数器
    public static Object producer = new Object();//用于wait notify
    public static Object consumer = new Object();
    public static ArrayList<Integer> list = new ArrayList<>();//此模型作为生产消费的仓库

    public void producer() throws InterruptedException {
        int item;
        for (int i = 0; i < 5; i++) {//当循环次数变大时，出错的机率就会变得很大
            item = 1;
            if (count == N) {
                synchronized (producer) {
                    producer.wait();
                }
            }
            count = count + 1;
            System.out.println("method producer count is: "+count );//打印count的日志
            list.add(item);
            if (count == 1) {
                synchronized (consumer) {
                    consumer.notify();
                }
            }
        }
    }

    public void consumer() throws InterruptedException {
        for (int i = 0; i < 3; i++) {//当循环次数变大时，出错的机率就会变得很大
            if (count == 0) {
                synchronized (consumer) {
                    consumer.wait();
                }
            }
            count = count - 1;
            System.out.println("method consumer count is: "+count );//打印count的日志
            if (count == N - 1) {
                synchronized (producer) {
                    producer.notify();
                }
            }

                list.remove(list.size() - 1);
        }

    }

    public static void main(String[] args) {
        //运行消费者线程
        Thread t2 = new Thread(()->{
            try {
                new testMultiThread().consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();
        //运行生产者线程
        Thread t1 = new Thread(()->{
            try {
                new testMultiThread().producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        //记录结果线程
        Thread t3 = new Thread(() ->{
            System.out.println("*********************");
            System.out.println("count is : " + count);
            System.out.println("ArraList is  : " + list.toString());
            System.out.println("*********************");
        });
        while (true) {
            //直到t1和t2完成后，才输出count和ArrayList的最终结果
            if (!t1.isAlive() && !t2.isAlive()) {
                t3.start();
                break;
            }
        }

    }
}

```



**信号量**：down为减一，up为加一，再信号量操作完成之前（加一减一的过程），其余进程不得访问信号量
互斥量：就是信号量的简化版本，只能表示0和1

用信号量解决生产者——消费者问题
解释一下：
- empty为0，表示库存是满的（可以这么记忆 !empty(),不为空），生产者不能再生产，大于0表示库存有空间可以继续生产（empty()==true）,生产完毕后库存empty--，full++（full为0时表示仓库是空的，为N的时候表示仓库是满的）
- 至于为什么要在insert_item前加down(&mutex)，是因为要保证insert_item操作的隔离性
- down(&empty)中empty初始为N，表示可以有多个生产者进程同时生产（注意down的实现过程中就是读取修改时，也就是减一的过程完成前，其他进程无法访问empty）
- down(&empty)一定要放再down(&mutex)前面，如果放在了后面，如果mutex为1（进入临界区后变成0），empty为0，则生产者睡眠。由于mutex是0，消费者就始终无法进行消费操作！死锁！
- 正确做法，down(&empty)再down(&mutex)前面。这样如果empty为0，生产者睡眠，而则生产者不能mutex进入临界区。这样就不会阻塞消费者的mutex临界区啦
- 以上原因也是为什么要用两个变量empty和full的原因，如果生产者empty为0，消费者也用empty作为信号量，就会死锁。

```c
#define N 100
typedef int semaphore;
semaphore mutex = 1;
semaphore empty = N;
semaphore full = 0;

void producer() {
    while(TRUE){
        int item = produce_item(); // 生产一个产品
        // down(&empty) 和 down(&mutex) 不能交换位置，否则造成死锁
        down(&empty); // 记录空缓冲区的数量，这里减少一个产品空间
        down(&mutex); // 互斥锁
        insert_item(item);
        up(&mutex); // 互斥锁
        up(&full); // 记录满缓冲区的数量，这里增加一个产品
    }
}

void consumer() {
    while(TRUE){
        down(&full); // 记录满缓冲区的数量，减少一个产品
        down(&mutex); // 互斥锁
        int item = remove_item();
        up(&mutex); // 互斥锁
        up(&empty); // 记录空缓冲区的数量，这里增加一个产品空间
        consume_item(item);
    }
}

```
java实现
```java
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class testmultiThread1 {
    public static int N = 20;
    public static Semaphore mutex =new Semaphore(1);
    public static Semaphore remainSpace = new Semaphore(N);
    public static Semaphore currentProducts = new Semaphore(0);
    public static  ArrayList<Integer>  store = new ArrayList<>();

    public void producer() throws InterruptedException {
        int item = 1;

        for(int i = 0 ;i<50;i++){
            remainSpace.acquire();// 剩余空间-1
            mutex.acquire();
            store.add(item);
            System.out.println("producer is producing");
            mutex.release();
            currentProducts.release();//现有产品数量+1
        }

    }

    public void consumer() throws InterruptedException{
        int item = 1;

        for(int i = 0 ;i<50;i++){
            currentProducts.acquire();//现有产品-1
            mutex.acquire();
            store.remove(store.size()-1);
            System.out.println("consumer is consuming");
            mutex.release();
            remainSpace.release();//剩余空间+1
        }
    }

    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            try {
                new testmultiThread1().producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            try {
                new testmultiThread1().consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        while(t1.isAlive()||t1.isAlive());
        System.out.println("Over");
        System.out.print(testmultiThread1.store);
    }

}

```
## 管程

**管程**:管程时一个由过程、变量和数据结构等组成的一个集合。结构内的多个子程序（对象或模块）形成的多个工作线程互斥访问共享资源。

使用**信号量**机制实现的生产者消费者问题需要**客户端代码**做很多控制，而管程把控制的代码独立出来，不仅不容易出错，也使得客户端代码调用更容易。

管程是为了解决信号量在临界区的 PV 操作（就是down与up）上的配对的麻烦，把配对的 PV 操作集中在一起，生成的一种并发编程方法。其中使用了条件变量这种同步机制。

管程有一个重要特性：在一个时刻只能有一个进程使用管程。进程在无法继续执行的时候不能一直占用管程，否者其它进程永远不能使用管程。

管程引入了 条件变量 以及相关的操作：wait() 和 signal() 来实现同步操作。对条件变量执行 wait() 操作会导致调用进程阻塞，把管程让出来给另一个进程持有。signal() 操作用于唤醒被阻塞的进程。

java实现管程，处理消费者生产者的问题

```java
import java.util.ArrayList;

public class moniterThread {
    //缓存大小
    static final int N = 1024;
    //创建管程
    static our_moniter mon = new our_moniter();
    static producer p = new producer();
    static consumer c = new consumer();
    public static void main(String[] args){
        long start =  System.currentTimeMillis();
        p.start();
        c.start();

        while(p.isAlive()||c.isAlive());
        long end = System.currentTimeMillis();
        System.out.println("total run time is:" + (end-start));
    }

    //生产者线程
    static class producer extends Thread {
        public void run(){
            for(int i = 0;i<10000;i++){
                try {
                    mon.insert(i);//调用管城
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //消费者线程
    static class consumer extends Thread{
        public void run(){
            ArrayList<Integer> res = new ArrayList<>();
            for(int i = 0 ;i<10000;i++){
                int r = 0;
                try {
                    r = mon.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                res.add(r);
            }
            System.out.println(res.toString());
        }
    }
    //管程
    static class our_moniter{

        //缓存大小
        private int buffer[] = new int[N];
        private int count = 0,left=0,right=0;
        //添加
        public synchronized void insert(int item) throws InterruptedException {
            if(count == N) wait();
            buffer[right] = item;
            right = (right+1)%N;
            System.out.println("is adding a product");
            count++;
            if(count == 1) notify();
        }
        //删除
        public synchronized  int  remove() throws InterruptedException {
            if(count == 0) wait();
            int item = buffer[left];
            left = (left+1)%N;
            System.out.println("is removing a product");
            count--;
            if(count == N-1) notify();
            return item;
        }
    }
}

```



1. 读者-写者问题
允许多个进程同时对数据进行读操作，但是不允许读和写以及写和写操作同时发生。读者优先策略

Readercount：读操作的进程数量（Rcount=0）

ReaderMutex：对于Readercount进行加锁（CountMutex=1），让这个值的加减具有隔离性

WriteMutex：互斥量对于写操作的加锁（WriteMutex=1）

```java
import java.util.concurrent.Semaphore;

public class ReadAndWriteConcurrent {
    public static int  writerOutput =0;
    public static int ReaderCount = 0;
    public static Semaphore ReadMutex = new Semaphore(1);
    public static Semaphore WriteMutex = new Semaphore(1);

    public   writer w = new writer();
    public   reader r = new reader();


    public static void main(String[] args){
        ReadAndWriteConcurrent test1 = new ReadAndWriteConcurrent();
        ReadAndWriteConcurrent test2 = new ReadAndWriteConcurrent();
        //测试两个写操作同时写
        /*test1.w.start();
        test2.w.start();*/

        //测试两个读操作
        test1.r.start();
        test1.w.start();
        test2.r.start();

    }

     class writer extends Thread {
        public void run() {
            for (int i = 0; i < 2; i++) {
                try {
                    WriteMutex.acquire();
                    System.out.println(++writerOutput);
                    WriteMutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     class reader extends Thread{
        public void run(){
            for(int i = 0; i<2; i++){
                try {
                    ReadMutex.acquire();//使用读者读取数据时，先确认写操作停了没有
                    if(ReaderCount == 0) //读者为0时，那么可能在写操作，读者不为0时，那么肯定没有进行写操作！
                        WriteMutex.acquire();//在写操作就会阻塞。不会让你读,阻塞在此。当写完成后获取写锁，读的时候不让写！
                    ReaderCount++;//没在读就增加一个读者进行读操作。
                    ReadMutex.release();//确认读操作完毕，已增加读者，释放读锁。
                    //DO READ
                    System.out.println("now read res is =" + writerOutput);

                    ReadMutex.acquire();//读完成后获取读锁，让ReaderCount减一隔离操作，不被其他读者影响
                    ReaderCount--;//读完了对读者减一
                    if(ReaderCount == 0)
                        WriteMutex.release();//读完成后，如果没有读者了，那么就释放写锁，允许写了。
                    ReadMutex.release();//读者减一完毕后(如果读者为0释放写锁后),最后释放读锁


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

```



