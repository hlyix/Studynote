# Kryo

kryo是一个只能用于java序列化的一个

## 基本使用方法

可以去官网看readme:https://github.com/EsotericSoftware/kryo/blob/master/README.md

maven的依赖下载

```java
<dependency>
   <groupId>com.esotericsoftware</groupId>
   <artifactId>kryo</artifactId>
   <version>5.0.0-RC4</version>
</dependency>
```



快速开始

```java
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.*;

public class HelloKryo {
   static public void main (String[] args) throws Exception {
      Kryo kryo = new Kryo();
      kryo.register(SomeClass.class);

      SomeClass object = new SomeClass();
      object.value = "Hello Kryo!";
		//此处也可以用byte数组来代替outputStream
      Output output = new Output(new FileOutputStream("file.bin"));
      kryo.writeObject(output, object);
      output.close();

      Input input = new Input(new FileInputStream("file.bin"));
      SomeClass object2 = kryo.readObject(input, SomeClass.class);
      input.close();   
   }
   static public class SomeClass {
      String value;
   }
}
```

1. 首先我们一定得注册我们序列化的类，不然无法反序列化出来
2. 被序列化的类最好实现自己的constructor方法，会使得性能提升

3. 也可以将上面的Output和Input替换为ByteBufferOutput和ByteBufferInput，后者利用的是缓存，可以将数组存在ByteBuffer（java自带的Nio类）中，而不是在byte数组中。

利用byte数组实现

```java
      HelloServiceImpl helloService = new HelloServiceImpl();

        byte[] bytes = new byte[1024 * 1024];

        Output output = new Output(bytes);
        kryo.writeObject(output, helloService);
        output.close();

        Input input = new Input(bytes);
        HelloService object2 = kryo.readObject(input, HelloServiceImpl.class);
        input.close();
        System.out.println(object2.hello());
```



利用buffer数组实现

```java
        HelloServiceImpl helloService = new HelloServiceImpl();

        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
        ByteBufferOutput bufferOutput = new ByteBufferOutput(buffer);
        kryo.writeObject(bufferOutput,helloService);
        bufferOutput.close();

        ByteBufferInput bufferInput = new ByteBufferInput(buffer);
        HelloService o1 = kryo.readObject(bufferInput,HelloServiceImpl.class);
        System.out.print(o1.hello());
```



## 在Dubbo中的应用

此文介绍了如何在dubbo项目中设置kryo

http://dangdangdotcom.github.io/dubbox/serialization.html