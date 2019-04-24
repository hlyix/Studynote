# 一、自动内存管理机制
![数据结构.png](pic/1.png)
## 1. 程序计数器
- 程序计数器是一块较小的内存空间，字节码解释器通过改变计数器的值来选取下一条需要执行的字节码指令。（分支、循环、跳转、异常处理）
- 线程私有，确保线程切换后能恢复到正确的位置
- 如果执行native方法，计数器值为空

## 2. Java虚拟机栈
- 线程私有
- 每个方法执行时都会创建一个栈帧，包含：
a)局部变量表b)操作数栈c)动态链接d)方法出口
>  - 局部变量表:编译期可知的基本数据类型(int byte long...)、对象引用(reference)、return的类型。---其中除了double和long占两个局部变量空间slot，其他都是一
## 3. 本地方法栈
- 本地方法一般是用其它语言（C、C++ 或汇编语言等）编写的，并且被编译为基于本机硬件和操作系统的程序，对待这些方法需要特别处理。
- 本地方法栈与 Java 虚拟机栈类似，它们之间的区别只不过是本地方法栈为本地方法服务。
## 4. Java堆
- 存放对象实例，所有对象实例存放在此处
- 不连续内存，逻辑上连续即可，可以扩展
## 5. 方法区
- 类信息class_info
- 常量
- 静态变量
- 编译器编译后的代码等数据（字节码）

## 6. 运行时常量池
- 类文件的常量池->字面量和符号引用
- 编译后的直接引用
- 并编译期才产生，运行期调用String的intern()函数会产生

## 7. 直接内存
- 不是运行时数据区的一部分
- 例如NIO，基于通道(Channel)与缓冲(Buffer)的I/O方式，他可以直接使用Nativeh函数直接分配堆外内存，然后通过储存在Java堆中的DirectByteBuffer对象作为这块内存的引用进行操作。在一些操作中避免在Native堆和java堆中频繁引用数据
- 受到本机内存（RAM）限制，如果java堆过大，导致直接内存过小，便会溢出。
## 对象的访问
![1](pic/2.jpg)
![1](pic/3.jpg)

## outofmemory实战
### 1. 堆溢出
```java
import java.util.ArrayList;
import java.util.List;
/* VM conf:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError*/
//设置最小和最大值为20m，那么堆就不会自动扩展了
public class HeapOverflow {
    static class OOMObject {
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
```
输出结果
```
java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid15340.hprof ...
Heap dump file created [28226884 bytes in 0.155 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:265)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:231)
	at java.util.ArrayList.add(ArrayList.java:462)
	at HeapOverflow.main(HeapOverflow.java:14)

Process finished with exit code 1

```
情况分析：
- 内存泄漏，可以用Jprofiler查看GC Roots的相关联，是如何与GCroot相连，导致无法回收
- 如不存在泄漏，检查堆参数-Xms -Xmx,是否可以调大

### 2. 栈溢出
Java虚拟机定义了两种栈异常
- 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出stackoverflowerror的异常
- 如果虚拟机在扩展栈时，无法分配到足够的内存空间，则会抛出outofmemoryerror的异常

单线程测试基本上都是stackoverflowerror异常（下面代码）
栈溢出测试的两种方法：
- 使用-Xss参数减少栈内存的容量。结果：抛出StackOverflow异常，异常出现时堆栈深度相应缩小
- 


# 类文件结构

