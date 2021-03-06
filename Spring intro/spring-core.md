# 第一部分

## 一、Spring的核心

- Spring的核心是IOC（控制反转）和面向切面(AOP)
  - 核心容器 ： beans、core、context、SpEL

  ![springframwork](pic/spring.png)

再创建Spring项目时记得导入4个核心一个依赖

Spring的优点
- 方便解耦，简化开发（高内聚低耦合）
  - **spring就是一个大工厂**，可以将所有对象创建和依赖关系维护，交给Spring管理。
  - Spring工程时用于生产bean，就不用再程序中new出来了
  - 用接口，例如一个IUser，其他实现所有的类都可以用IUser创建，那么我们只需在bean中改变IUser的各种实现类，相当于换了插头的接口，十分方便实现类的更换

- AOP编程的支持
  - 可以方便实现对程序进行权限拦截、运行监控等功能

- 方便程序的测试
  - spring对junit4支持，可以通过注解方便的测试Spring程序

- 方便集成各种优秀的跨国家
  - 对各种优秀框架（如：struct、Hibernate、MyBatis、Quartz等）的直接支持

- 降低JavaEE API的使用难度
  - 例如对JDBC、JavaMail、远程调用等都提供了封装，使这些API应用难度大大降低。  

## 二、配置文件
Spring 的配置文件applicationContext.xml一般放在classpath下（idea如有其他的classpath可在module中添加source为classpath）

内容就是schema

Spring的配置文件是基于XML格式的，Spring1.0的配置文件采用DTD格式，Spring2.0以后使用Schema的格式，后者让不同类型的配置拥有了自己的命名空间，使配置文件更具有扩展性。


spring头部配置文件详情可以见：https://blog.csdn.net/qq646040754/article/details/81531151


## 三、DI depencency injection

在依赖注入的模式下，创建被调用者得工作不再由调用者来完成，创建被调用者实例的工作通常由Spring容器完成，然后注入调用者。创建对象时，**向类里的属性设置值**

### 1）为什么要实现松耦合
上层调用下层，上层依赖于下层，当下层剧烈变动时上层也要跟着变动，这就会导致模块的复用性降低而且大大提高了开发的成本。

一般情况下抽象的变化概率很小，让用户程序依赖于抽象，实现的细节也依赖于抽象。即使实现细节不断变动，只要抽象不变，客户程序就不需要变化。这大大降低了客户程序与实现细节的耦合度。

### 2）IOC和DI区别

IOC控制反转，把对象创建交给Spring配置
DI依赖注入，向类里面属性注入值
关系，依赖注入不能单独存在，需要在IOC基础上完成操作（bean中的ref）

### 3)依赖注入方式

- 使用set方法注入
- 使用有参构造注入
- 使用接口注入

#### (1) set方法注入

```xml
    <bean id ="school01" class = "com.ioc.SchoolImpl">
        <!--set方法注入属性
    name属性值：类中定义的属性名称，这里是location
    value属性值：设置具体的值，相当于location = "HuNan University"
-->
        <property name="location" value="HuNan University"></property>
    </bean>
```

```java
public class SchoolImpl implements School {

    String location;

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void Register() {
        System.out.println("you have resgisted in "+location);
    }
}

```

#### (2)使用有参构造注入

```java
public class Person {
    private String pname;

    public void setPname(String pname) {
        this.pname = pname;
    }
}
```
```xml
<bean id="user" class="cn.wang.ioc.User">
        <!--构造方法注入属性-->
        <constructor-arg name="pname" value="Tony"></constructor-arg>
</bean>

```

#### (3) 注入对象类型属性

```java
public class UserService {
    // 1.定义UserDao类型属性
    private UserDao userDao;

    // 2.生成set方法
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

```xml
<bean id="userDao" class="cn.wang.property.UserDao">
        <property name="name" value="Tom"></property>
    </bean>
    <bean id="userService" class="cn.wang.property.UserService">
        <!--name属性值：UserService类里的属性名称-->
        <!--ref属性:UserDao类配置bean标签中的id值-->
        <property name="userDao" ref="userDao"></property>
    </bean>
```


## 四、核心API
![classInhe](pic/classInhe.png)

### 1）BeanFactory
- 是一个工厂，用于生成bean
- 采用延迟加载，第一次getBean时才会初始化bean实例。([懒汉式饿汉式加载相关](https://blog.csdn.net/qq_35098526/article/details/79893628))


### 2）ApplicationContext
- 是Beanfactory的子接口，功能更强大
-  国际化处理、事件传递、Bean自动装配、各种不同应用层
- application初始化时，就会直接生成bean实例
- 
![直接加载](pic/application-context.png)

### 3）ClassPathXmlApplicationContext和FileSystemXmlApplicationContext
ClassPathXMLContext：用于加载classpath下载的xml（这个见得多）
FileSystemXmlApplicationContext用于加载指定盘符下的xml（绝对路径），可以通过java web ServletContext.getRealPath()获得指定盘符


## 五、基于XML装配Bean

实例化Bean的方式：
- 默认构造：就是普通的bean
- 静态工厂：自己写个工厂，专门用静态方法生成类实例
- 实例工厂：实例工厂，先new个实例工厂，再用实例工厂生成类实例

详细见：https://blog.csdn.net/small__snail__5/article/details/87903578

1. 默认构造
```<bean id = "" class ="">```必须提供的默认构造

2. 静态工厂：利用自己的工厂生产类
- 常用于与Spring整合其他框架（工具）
- 静态工厂：用于生成实例对象，**所有的方法必须是static**
```<bean id ="" class="工厂全限定类名" factory-method="静态方法">```
```<bean id ="UserService" class="com.package.MyBeanFactory" factory-method="createService">```

3.实例工厂：用自己的实例工厂生产类
- 必须先有工厂实例对象，通过实例对象创建对象，**提供所有的方法必须是“非静态的”**

```xml
<!--创建工厂实例-->
<bean id="myBeanFactory" class="com.package.MyBeanFactory"></bean>
<!--工厂生成实例-->
<bean id="UserService" facotry-bean="myBeanFactoryId" factory-method="createService">
```

## 六、Bean的种类

- 普通bean: 之前操作都是普通bean。```<bean id = "" class="A">```
, spring直接创建A实例，并返回
- FactoryBean：是一个特殊的bean，具有工厂生产对象的能力，只能生成特定的对象。bean必须实现FactoryBean接口，此接口提供方法getObject()用于获得特定的bean。
	<bean id = "" class="">先创建FB实例，使用调用getObject()方法，并返回方法的特定值。
- BeanFactory和FactoryBean的对比
  - beanfactory：生产任意的Bean
  - FactoryBean：特殊的bean，用于生成另一个特定的bean。
  - 详细见https://www.cnblogs.com/aspirant/p/9082858.html

## 七、作用域

用于确定spring创建bean实例的个数
- singleton单例：在IOC容器仅存在一个Bean实例，Bean以单例方式存在，默认值
- prototype：每次从容器中调用Bean时，都会返回一个新的实例，即每次调用getBean()时都相当于执行new XXXBean()

配置信息：
```xml
 <bean id="userSingleton" class="com.scope.UserServiceImp" scope="singleton"/>
```

## 八、生命周期
![Bean生命周期](https://img-blog.csdn.net/20160417164808359?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

### 1） 初始化和销毁
- 目标方法执行前和执行后，将进行初始化或销毁
- 初始化一般用来准备数据
- 销毁方法一般用于清理资源。
```xml
<bean id="" class=""  init-method="初始化方法名称" destory-method = "销毁方法名称">
```
我们直接在类中实现void init-method()方法和 destroy-method（）方法配置到bean中就好了

- 销毁方法的执行，容器必须close。```applicationContext.getClass().getMethod("close").invoke(applicationContext);```此方法接口中没有定义，由实现类提供

### 2) BeanPostProcessor后处理bean
- spring提供一种机制，只要实现此接口BeanPostProcessor,并将实现类提供给spring容器，spring容器将自动执行，在初始化方法前执行before()，在初始化方法后执行after()
- factroy hook that allows for custom modification of new bean instances,e.g. checking for marker interfaces or wrapping them with proxies.
- spring提供工厂钩子，用于修改实例对象，可以生成代理对象，是AOP底层
- 
模拟过程：视频学习地址 https://www.bilibili.com/video/av15369076/?p=10
```java
A a = new A();
a = B.before(a);//将a的实例对象传递给后处理bean，可以生成代理对象并返回，就是给a加几个方法，到最后a就多出了几个功能，又不用直接在类A中加方法块代码，方便修饰类
a.init();
a = B.after(a);
a.addUser(); //生成代理对象，目的在目标方法前后执行（例如：开启事务、提交事务）
a.destory()
```
## 九、装配bean的其他简洁方法
### 1）P命名空间【了解】
- 对 setter方法注入进行简化，替换<property>,而是在```<bean p:属性名="普通值">```
- 配置文件头部加入```xmlns:p"http://www.springframework.org/schema/p"```

### 2）SpEL【了解】
- 对<property>进行统一编程，所有的内容都使用value
```<property name = "" value = "#{表达式}">```
#{123}、#{'jack'}数字和字符串
#{beanId} ： 另一个bean引用
#{beanId.propName} ： 可以操作数据属性
#{beanId.toString} : 执行方法
#{T(类).字段|方法}：静态方法或字段

### 3)集合
https://www.cnblogs.com/xiaostudy/p/9534164.html

## 十、注解装配Bean
- 注解：就是一个类，使用@注解名称
- 开发中：使用注解，取代xml配置文件。
- 使用前提，添加命名空间，让spring扫描含有注解的类
![12](pic/namespace.png)

### 1）@Component(id) 取代<bean.....>
需要在配置文件中添加<context:component-scan base-package="需要扫描的包"/>

### 2) web开发，提供了3个@Component注解衍生注解（功能一样）
- @Repository:dao层
- @Service: service层
- @Controller：web层

### 3） 依赖注入，可以给私有字段设置，也可以给setter方法设置
- 普通值：@Value("")
- 引用值：
  - 方式1：按照【类型】注入
    - @Autowired：缺点如果一个接口有两个实现类，那么他不知道拿哪一个
  - 方式2：按照【名称】注入
    - @Autowired
    - @Qualifier("名称")
  - 方式3：按照【名称】注入
    - @Resource("名称")

### 4） 生命周期
- 初始化：@PostConstruct
- 销毁： @PreDestroy

### 5) 作用域
- @Scope("prototype")多例

# 第二部分

## 一、AOP
利用AOP可以对业务逻辑的各个部分进行隔离。从而是的业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发效率。
- 经典应用：事务管理、性能监视、安全检查、缓存、日志等
- AOP采取**横向抽取**机制，取代了传统**纵向继承**体系重复性代码。
  - 横向抽取：组合附属功能，生成代理类
    - 可以织入多个切面，实现类似多重继承的功能
    - 切换方便，如果向去掉某些类的公共功能，直接修改连接点即可。（继承的话需要修改子类，切换到继承其他的父类）
  - 纵向继承：继承父类，在添加功能
- Spring AOP使用纯java实现，不需要专门的编译过程和类加载器，在运行期间通过代理方式向目标类织入增强代码


## 二、AOP实现原理
- AOP底层采用代理机制实现。
- 接口 + 实现类：spring采用**jdk的动态代理**
- 实现类:spring采用**cglib字节码增强** 

## 三、AOP专业术语
1. Target（目标类）：需要被代理的类，例如：UserService
2. Joinpoint（连接点）：指那些可能被拦截到的方法，例如：所有的方法
3. Pointcut（切入点）：被增强的连接点。
4. advice(通知/增强)：增强joinpoint的代码。例如：before(){...}、after(){...}
5. weaving(织入)：是指把增强的advice代码应用到目标对象target来创建新的代理对象proxy的过程
6. proxy（代理类）：aop生成的类
7. Aspect（切面）：Pointcut切入点和Advice通知的结合

## 四、JDK动态代理 对“装饰者设计模式”进行简化，使用前提是必须有接口
1. 目标类：接口+实现
2. 切面类：用于存通知MyAspect
3. 工厂类：编写工厂生成代理
4. 测试

目标类
```java
public interface UserService {
     void addUser();
     void updateUser();
     void deleteUser();

}

public class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("add a User");
    }

    @Override
    public void updateUser() {
        System.out.println("update a user");
    }

    @Override
    public void deleteUser() {

        System.out.println("delete a user");
    }
}


```
切面类
```java
public class MyAspect {
    public void before(){
        System.out.println("前置执行方法");
    }

    public void after(){
        System.out.println("后置执行方法");
    }
}

```

工厂类
```java
public class MyBeanFactory {

    public static UserService createServie() {

        //1.目标类
        UserService userService = new UserServiceImpl();
        //2.切面类
        MyAspect myAspect = new MyAspect();
        /*3.代理类:将目标类和切面类结合————>切面
        *
        * Proxy.newProxyInstance
        *   参数1：loader,类加载器，动态代理类运行时创建，任何类都需要类加载器将其加载到内存
        *   一般情况：当前类.class.getClassLoader();
        *           目标类.getClass().get....
        *   参数2： Class[] interfaces 代理类需要实现的所有接口
        *           方式1：目标类实例.getClass().getInterfaces();注意：只能获得自己接口，不能获得父元素接口
        *           方式2：new Class[]{UserService.class}
        *           例如:jdbc -————> DriverManager 获得接口Connection 这个是父接口只能用方式2实现
        *   参数3： InvocationHandler 处理类、接口，必须进行实现类，一般采用的都是匿名内部类的方式
        *       提供了invoke方法，代理类的每一个方法执行时，都将去调用一次invoke
        *           参数31：Object proxy：代理对象
        *           参数32：Method method：代理对象当前执行的芳芳描述对象（反射）
        *           参数33：Object[]args，目标类方法的参数
        * */

        UserService proxyService = (UserService) Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader(),
                userService.getClass().getInterfaces(),
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //前置方法
                myAspect.before();
                //目标类执行方法
                Object obj = method.invoke(userService,args);
                //后置方法
                myAspect.after();
                return obj;
            }
        });
        return proxyService;

    }
}

```
测试类
```java

public class TestDemo {
    @Test
    public void demo02(){
        MyBeanFactory myBeanFactory = new MyBeanFactory();
        UserService userService = myBeanFactory.createServie();
        userService.addUser();
        userService.deleteUser();
        userService.updateUser();
    }
}

```

## 五、CGLib字节码增强
- 没有接口，只有实现类
- 采用字节码增强框架cglib，在运行时创建目标类的子类，从而对目标类进行增强
- 导入jar包，或者maven
工厂类
```java
public class MybeanFactory {
    public static UserServiceImpl createService() {
        //1.目标类
        UserServiceImpl userService = new UserServiceImpl();
        //2.切面类
        MyAspect myAspect = new MyAspect();
        //3.代理类，采用cglib，底层创建目标类的子类

        //3.1核心类
        Enhancer enhancer = new Enhancer();
        //3.2确定父类
        enhancer.setSuperclass(userService.getClass());
        /*3.3设置回调函数，MethodInterceptor接口等效于jdk代理的 InvocationHandler接口
        *
        * intercept等效jdk invoke()
        * 参数1、参数2、参数3：InvocationHandler一样
        * 参数4：methodProxy方法代理
        */
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

                //前
                myAspect.before();
                //方式1
                //执行目标类的方法
                Object obj = method.invoke(userService,args);
                //方式2
                //执行代理类的父类
                methodProxy.invokeSuper(proxy,args);
                //后
                myAspect.after();
                return obj;
            }
        });

        //3.4 创建代理
        UserServiceImpl proxyService = (UserServiceImpl) enhancer.create();
        return proxyService;
    }
}

```
六、Spring半自动设置AOP

配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--1. 创建目标类-->
    <bean id = "userServiceId" class="com.semiAutoAOP.UserServiceImpl"/>

    <!--2. 创建切面类-->
    <bean id ="myAspectId" class="com.semiAutoAOP.MyAspect"/>

    <!--3. 创建代理类-->
    <!--使用工厂bean FactoryBean，底层调用getObject()，返回特殊bean，这里使用的ProxyFactoryBean生成特殊的代理bean-->
    <bean id="proxyServiceId" class="org.springframework.aop.framework.ProxyFactoryBean">
        <!--确定jdk代理的接口类们，可以通过<array>设置多个接口值，表示给多个接口和类添加环绕通知-->
        <property name="interfaces" value="com.semiAutoAOP.UserService"/>
        <!--确定目标类-->
        <property name="target" ref="userServiceId"/>
        <!--确定切面类-->
        <property name="interceptorNames" value="myAspectId"></property>

    </bean>

</beans>
```

其他的都差不多
这里简单写下切面类
```java
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAspect implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("前");
        //手动执行目标方法
        methodInvocation.proceed();
        System.out.println("后");
        return null;
    }
}

```
测试
```java
    @Test
    public void demo() {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/semiAutoAOP/applicationContext.xml");
        UserService userService = (UserService) context.getBean("proxyServiceId");
        userService.addUser();
    }
```
结果
```console
前
a_ioc add user
后

```

## 六、Spring AOP的配置（全自动）

- aop编程
  - 一定要导入aspectjweaver、springAOP和AOPappliance的包
  - 导入命名空间
  - 使用```<aop:config>```进行配置，如果使用标签：```proxy-target-class="true"``` 声明时使用cglib代理
    - ```<aop:pointcut>```切入点，从目标对象获得具体方法
    - ```<aop:advisor>```特殊的切面，只有一个通知和一个切入点
      - ```advice-ref```通知引用
      - ```pointcut-ref```切入点引用

配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/aop
                            http://www.springframework.org/schema/aop/spring-aop.xsd">
    <bean id="userServiceId" class="com.AOP.UserServiceImpl"/>
    <bean id="myAspectId" class="com.AOP.MyAspect"/>
    <aop:config>
	<!--exucution代表执行的目标类，第一个*代表返回任意值，第二个*代表任意类，第三个*代表任意方法，(..)代表方法内的参数任意-->
        <aop:pointcut expression="execution(* com.AOP.*.*(..))" id="myPointCut"/>
        <aop:advisor advice-ref="myAspectId" pointcut-ref="myPointCut"/>
    </aop:config>
</beans>
```

## 七、AspectJ
### 1）介绍
- AspectJ时基于java语言的AOP框架
- spring2.0以后新增了对AspectJ切点表达式支持
- @Aspect是AspectJ1.5新增功能，通过注解技术，允许直接在Bean类中定义切面
新版本的Spring框架，建议使用AspectJ方式来开发AOP
- 主要用途：自定义开发

### 2）切入点表达式
1. execution（） 用于描述方法
   - 语法：execution（修饰符 返回值 包.类.方法（参数）throws 异常）
     - 修饰符，一般省略
       - public  公共方法
       - *       任意
     - 返回值
       - void    返回空
       - String  返回字符串
       - *       返回值任意
     - 包，[省略]
       - com.AOP.crm              固定包
       - com.AOP.crm.*.service    代表crm包下面任意包目录下service（例如：com.AOP.crm.staff.service）
       - com.AOP.crm..            代表crm包下面所有子包
       - com.AOP.crm.*.service..  代表crm包下面任意包目录下servicex下的所有子包 
     - 类，[省略]
       - UserServiceImpl          指定类
       - *Impl                    以Impl结尾的类
       - User*                    以User开头
       - ’*‘                        任意
     - 方法，不能省略（具体使用和前面差不多，就多一个参数）
       - ()          表示无参
       - (int)       一个参数
       - (int, int)  两个int参数
       - (..)        表示任意参数
       - throws可省略，一般不用

    - 综合```<aop:pointcut expression="execution(省略)||execution(省略)" id="myPointCut"/>```其中||代表还要插入其他类路径作为切点
2. bean(id)对指定的bean所有的方法切入，例如bean("userServiceId")



### 3) AspectJ通知类型

- before：前置通知
- afterReturning：后置通知（常用于返回值校验）
- around：环绕通知，必须手动执行目标方法
- afterThrowing：方法抛出异常后执行，无异常不执行
- after：最终通知

```java
//环绕通知
try{
    //前置：
    before();
    //手动执行目标方法
    //后置
    afterReturning();
}catch(){
    //抛出异常 
    afterThrowing();
}finally{
    //最终 
    after();
}

```

记得导入aop-alliance、spring-aop、aspect、spring-aspect

### 4）基于xml

1. 目标类：接口+实现

2. 切面类：编写多个通知，采用aspectj通知名称任意（方法名任意）

3. aop编程，将通知应用到目标类

4. 测试

   ```xml
   <aop:config>
       <aop:aspect ref="myAspectId">
           <!--子元素中有
   <aop:pointcut>
   <aop:before>
   <aop:after>
   <aop:afterReturning>
   <aop:afterThrowing>
   -->
           <aop:pointcut expression="execution(* com.package.UserServiceImpl.*(..))" id="myPointcut">
           </aop:pointcut>
           <!--前置通知，method：通知即方法名。pointcut:切入点表达式，pointcut-ref切入点引用，可与其他通知共享切入点-->
           <!--通知方法可以有参数
            格式：public void myBefore(JoinPoint joinPoint)
   		参数1：org.aspectj.lang.JointPoint 用于描述连接点（目标方法），获得目标方法方法名等
   -->
           <aop:before method="myBefore" pointcut-ref="myPointcut"/>
           <!--后置通知
   		<aop:after-returning method="" pointcut-ref="" returning="ret"/>
   		returning是通知方法的第二个参数名称
   		通知方法的格式：public void myAfterReturning(JoinPoint joinPoint,Object ret)
   		参数1：连接点描述
   		参数2：类型Object，参数名returning="ret"配置的,表示是目标方法的返回值
   -->
           <!--环绕通知
   		<aop:around method="" pointcut-ref=""/>
   		通知方法格式:public Object myAround(ProceedingJoinPoint joinPoit) throws Throwable{}
   		返回值类型：Object
   		方法名：任意
   		参数：org.aspectj.lang.ProceedingJoinPoint
   		抛出异常
   		执行目标方法：Object obj = joinPoint.proceed();
   		例如:<aop:around method="myAround" pointcut-ref="myPointCut">
   -->
       </aop:aspect>
   </aop:config>
   ```

### 5) 基于注解的切面配置

直接上实例

UserServiceImpl类

```java
@Service("userServiceId")
public class UserServiceImpl implements UserService {
    public void addUser() {
        System.out.println("a_ioc add user");
    }
    public void deleteUser() {
        System.out.println("a_ioc delete user");
    }
}
```

切面MyAspect类

```java
@Component
@Aspect
public class MyAspect  {
    @Around("execution(* com.AnnotationAOP.UserServiceImpl.*(..))")
    public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("前");
        //手动执行目标方法
        joinPoint.proceed();
        System.out.println("后");
        return null;
    }
   
    
    /*
    也可以直接设置pointcut切点
     @Pointcut()
    private void mypointcut("execution(* com.AnnotationAOP.UserServiceImpl.*(..))"){  
    }
    然后直接在@Around(value = "mypointcut")
    */
}
```

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/aop
                            http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--扫描bean配置-->
    <context:component-scan base-package="com.AnnotationAOP" />
    <!--自动扫描aop注解-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

测试类

```java
public class Test {
    @org.junit.Test
    public void demo(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/AnnotationAOP/applicationContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userServiceId");
        userService.addUser();
    }
}
```

### 6) aop注解总结

@Aspect 声明切面，修饰切面类，从而获得通知

通知：

@Before 前置

@AfterReturning 后置

@Around 环绕

@After 最终

@AfterThrowing 抛出异常

切入点：

@PointCut：修饰方法 ```private void xxxx(){ }```之后通过“方法名”获得切入点引用





# 第三部分 

## 事务管理

### 1 导入三个接口的包

- ```PlatformTransactionManager  ```平台事务管理器，spring要管理事务，必须使用事务管理器

​        进行事务配置时，必须**配置事务管理器**。

- ```TransactionDefinition```：事务详情（事务定义、事务属性），spring用于确定事务具体详情，

​        例如：隔离级别、是否只读、超时时间等

​        进行事务配置时，**必须配置详情**。spring将配置项封装到该对象实例。

![2](pic\transactiondef.png)

> 其中掌握着三个过渡行为
>
> 1）PROPAGATION_REQUIRED , required , 必须  【默认值】
>
> ​        支持当前事务，A如果有事务，B将使用该事务。
>
> ​        如果A没有事务，B将创建一个新的事务。
>
> 2）PROPAGATION_REQUIRES_NEW ， requires_new ，必须新的
>
> ​        如果A有事务，将A的事务挂起，B创建一个新的事务
>
> ​        如果A没有事务，B创建一个新的事务
>
> 3）PROPAGATION_NESTED ，nested ，嵌套
>
> ​        A和B底层采用保存点机制，形成嵌套事务。

- ```TransactionStatus```：事务状态，spring用于记录当前事务运行状态。例如：是否有保存点，事务是否完成。

​        spring底层根据状态进行相应操作。

![1](pic/transactionStatus.png)



三个包分别是：*spring-jdbc*  *spring-orm*  *spring-tx*



### 2 介绍注解类型的事务管理（最方便）

xml配置文件如下

不要忘记导入命名空间：http://www.springframework.org/schema/tx 
       					   http://www.springframework.org/schema/tx/spring-tx.xsd

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
       					   http://www.springframework.org/schema/beans/spring-beans.xsd
       					   http://www.springframework.org/schema/aop 
       					   http://www.springframework.org/schema/aop/spring-aop.xsd
       					   http://www.springframework.org/schema/context 
       					   http://www.springframework.org/schema/context/spring-context.xsd
       					   http://www.springframework.org/schema/tx 
       					   http://www.springframework.org/schema/tx/spring-tx.xsd">

	<!-- 创建数据源 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/study"></property>
		<property name="user" value="root"></property>
		<property name="password" value="10086"></property>
	</bean>
	
	<!-- 配置dao 这里和我的不一样，他是dao继承了jdbcDaoSupport，我直接用的是druid用@dao配置-->
	<bean id="accountDaoImple" class="cn.lm.tx03_xml.AccountDaoImple">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	
	<!-- 配置service -->
	<bean id="accountServiceImplId" class="cn.lm.tx03_xml.AccountServiceImpl">
		<property name="accountDao" ref="accountDaoImple"></property>
	</bean>
	
<!-- 4 事务管理 -->
	<!-- 4.1 事务管理器 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<!-- 4.2 将管理器交予spring 
		* transaction-manager 配置事务管理器
		* proxy-target-class
			true ： 底层强制使用cglib 代理
	-->
	<tx:annotation-driven transaction-manager="txManager"/>

	
	<!-- AOP编程，目标类有ABCD（4个连接点），切入点表达式 确定增强的连接器，从而获得切入点：ABC -->
	<aop:config>
		<aop:advisor advice-ref="txAdvice" pointcut="execution(* cn.lm.tx03_xml.*.*(..))"/>
	</aop:config>
</beans>


```

要配置事务的服务（类或者类中的方法加注解都行）

```java
@Transactional(propagation=Propagation.REQUIRED , isolation = Isolation.DEFAULT)
public class AccountServiceImpl implements AccountService {

```



