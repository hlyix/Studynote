## Spring的核心

- Spring的核心是IOC（控制反转）和面向切面(AOP)
  - 核心容器 ： beans、core、context、SpEL

  ![springframwork](pic/spring.png)

再创建Spring项目时记得导入4个核心一个依赖

Spring的优点
- 方便解耦，简化开发（高内聚低耦合）
  - **spring就是一个大工厂**，可以将所有对象创建和依赖关系维护，交给Spring管理。
  - Spring工程时用于生产bean，就不用再程序中new出来了
 
- AOP编程的支持
  - 可以方便实现对程序进行权限拦截、运行监控等功能

- 方便程序的测试
  - spring对junit4支持，可以通过注解方便的测试Spring程序

- 方便集成各种优秀的跨国家
  - 对各种优秀框架（如：struct、Hibernate、MyBatis、Quartz等）的直接支持
 
- 降低JavaEE API的使用难度
  - 例如对JDBC、JavaMail、远程调用等都提供了封装，使这些API应用难度大大降低。  

## 配置文件
Spring 的配置文件applicationContext.xml一般放在classpath下（idea如有其他的classpath可在module中添加source为classpath）

内容就是schema

Spring的配置文件是基于XML格式的，Spring1.0的配置文件采用DTD格式，Spring2.0以后使用Schema的格式，后者让不同类型的配置拥有了自己的命名空间，使配置文件更具有扩展性。


spring头部配置文件详情可以见：https://blog.csdn.net/qq646040754/article/details/81531151


## DI depencency injection

在依赖注入的模式下，创建被调用者得工作不再由调用者来完成，创建被调用者实例的工作通常由Spring容器完成，然后注入调用者。创建对象时，**向类里的属性设置值**

### 为什么要实现松耦合
上层调用下层，上层依赖于下层，当下层剧烈变动时上层也要跟着变动，这就会导致模块的复用性降低而且大大提高了开发的成本。

一般情况下抽象的变化概率很小，让用户程序依赖于抽象，实现的细节也依赖于抽象。即使实现细节不断变动，只要抽象不变，客户程序就不需要变化。这大大降低了客户程序与实现细节的耦合度。

### IOC和DI区别

IOC控制反转，把对象创建交给Spring配置
DI依赖注入，向类里面属性注入值
关系，依赖注入不能单独存在，需要在IOC基础上完成操作（bean中的ref）

### 依赖注入方式

- 使用set方法注入

- 使用有参构造注入

- 使用接口注入