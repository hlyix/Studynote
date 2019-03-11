1.pojo类
用于存放数据库内的实体
2.Mapper
是一个接口用于查询（可用代理实例化），对应Mybatis中的字段
3.Service接口
服务类，用于处理Mapper取出数据库，做对应的服务。
4.ServiceImp类
用于实现服务接口的类
5.Controller
用于连接网页，用@RequestMapping（""）连接到前端，获取httpsession的数据。一般是获取放入Model类，然后用Service类处理后的数据传输到Model中。
```
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
  
    @RequestMapping("admin_category_list")
    public String list(Model model){
        List<Category> cs= categoryService.list();
        model.addAttribute("cs", cs);
        return "admin/listCategory";
    }
}
```


# 关于SpringMVC的配置文件
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd">
 
    <!--启动注解识别-->
    <context:annotation-config/>
 
    <context:component-scan base-package="com.how2java.tmall.controller">
        <context:include-filter type="annotation"
                                expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
 
    <mvc:annotation-driven />
 
    <!--开通静态资源的访问-->
    <mvc:default-servlet-handler />
 
    <!-- 视图定位 -->
    <bean
            class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass"
                  value="org.springframework.web.servlet.view.JstlView" />
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
    </bean>
 
    <!-- 对上传文件的解析-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
</beans>
```

## 然后修改web的文件
修改web.xml，主要提供如下功能
1. 指定spring的配置文件为classpath下的applicationContext.xml
2. 设置中文过滤器
3. 指定spring mvc配置文件为classpath下的springMVC.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         version="2.5">
 
  <!-- spring的配置文件-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
 
  <!--中文过滤器-->
  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
 
  <!-- spring mvc核心：分发servlet -->
  <servlet>
    <servlet-name>mvc-dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- spring mvc的配置文件 -->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springMVC.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>mvc-dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
 
</web-app>
```
