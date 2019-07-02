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


Spring 的配置文件一般放在classpath下（idea如有其他的classpath可在module中添加source为classpath）

