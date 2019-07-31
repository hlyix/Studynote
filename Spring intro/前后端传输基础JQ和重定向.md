## 利用JQ的ajax进行前后端传输

首先要理解一个概念：我们不能够使用直接打开html界面来请求后台，这是因为打开的端口号和后台端口号不一致，就会出现跨域请求的错误

跨域请求:

- 网络协议不同，如http协议访问https协议。
- 端口不同，如80端口访问8080端口。
- 域名不同，如qianduanblog.com访问baidu.com。
- 子域名不同，如abc.qianduanblog.com访问def.qianduanblog.com。
- 域名和域名对应ip,如www.a.com访问20.205.28.90.

具体见：https://www.cnblogs.com/minigrasshopper/p/8573519.html



所以此处我们利用SpringMVC来测试

```java
@Controller
public class controller {
    @RequestMapping("/test")
    @ResponseBody
    public void test(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject);
    }
}
```



一、先写好hello.html放在类目录的resources/static目录下

```html
<script src="jquery.min.js"></script>

<div id="checkResult"></div>

<input id="name" type="button" onclick="login()" value="post"></input>

<script type="text/javascript">
    function login() {
        var data = {"qid": "2", "id": "2", "page": "1"};
        $.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/test",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (message) {
                alert("提交成功" + JSON.stringify(message));
                $("#checkResult").html(message);
            }
        });
    }


</script>

```

二、在Springboot设置欢迎页面为我们的hello.html

```java

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/hello.html");

    }
}
```

三、然后访问localhost:8080端口，就会出现hello.html的界面，最后点击按钮即可发送数据到后台

```
后台打印的数据
{"id":"2","page":"1","qid":"2"}
```



