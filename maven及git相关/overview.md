## GitIgnore配置

```shell
//初始化
touch .gitignore
```

规则  作用
 /mtk    过滤整个文件夹
 *.zip   过滤所有.zip文件
 /mtk/do.c   过滤某个具体文件
 !/mtk/one.txt   追踪（不过滤）某个具体文件
 注意：如果你创建.gitignore文件之前就push了某一文件，那么即使你在.gitignore文件中写入过滤该文件的规则，该规则也不会起作用，git仍然会对该文件进行版本管理。

配置语法
 以斜杠“/”开头表示目录；
 以星号“*”通配多个字符；
 以问号“?”通配单个字符
 以方括号“[]”包含单个字符的匹配列表；
 以叹号“!”表示不忽略(跟踪)匹配到的文件或目录。
 注意： git 对于 .gitignore配置文件是按行从上到下进行规则匹配的

具体见：

1. https://jingyan.baidu.com/article/c85b7a64bb7979003aac955a.html
2. https://www.jianshu.com/p/74bd0ceb6182

# LInux上项目部署的自动化脚本配置

1.先下载git和maven

```shell
yum install -y maven
yum install -y git
```

2. pull代码到你自己的指定位置

```shell
git clone .....
git pull ....
```

3. 编写shell脚本

   ```shell
   echo "mvn starting packaging........."
   
   cd wechatHelp
   mvn clean install -Dmaven.test.skip=true
   
   echo "executing springboot project........"
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

   给脚本执行权限

   ```shell
   chmod -x deploy.sh
   ```

   

   这个脚本写完后，用source运行，这样就会用当前shell执行而不是子shell，子shell是无法进入上面cd wechatHelp文件夹中的。

   ```shell
   source deploy.sh
   ```

   

## Shell脚本菜鸟教程

https://www.runoob.com/linux/linux-shell.html

## maven打包jar包和war包及war包部署

jar ：```mvn clean install -Dmaven.test.skip=true```

执行后生成在当前目录的target目录中

war：```mvn clean package -Dmaven.test.skip=true```

打包是需要依赖jar包的，例如我的springboot项目就是在插件栏有以下依赖

```xml
  <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.DemoApplication</mainClass>
                    <layout>JAR</layout>
            </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

具体可参考

https://blog.csdn.net/piyongduo3393/article/details/88140841

## linux部署tomcat项目

https://blog.csdn.net/qq_35733535/article/details/79358154