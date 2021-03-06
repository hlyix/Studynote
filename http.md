## HttpServletRequest 的方法
**Request对象的常用方法：**
```request.getRequestURL(): 浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有)" 
request.getRequestURI(): 浏览器发出请求的资源名部分，去掉了协议和主机名" 
request.getQueryString(): 
请求行中的参数部分，只能显示以get方式发出的参数，post方式的看不到
request.getRemoteAddr(): 浏览器所处于的客户机的IP地址
request.getRemoteHost(): 浏览器所处于的客户机的主机名
request.getRemotePort(): 浏览器所处于的客户机使用的网络端口
request.getLocalAddr(): 服务器的IP地址
request.getLocalName(): 服务器的主机名
request.getMethod(): 得到客户机请求方式一般是GET或者POST
```
**Request对象获取request的参数：**
```
request.getParameter(): 是常见的方法，用于获取单值的参数,对应html的input的value
request.getParameterValues(): 用于获取具有多值的参数，
比如注册时候提交的 "hobits"，可以是多选的。获取的key-String[] = hobits-[[dota],[lol]] 
request.getParameterMap(): 用于遍历所有的参数，并返回Map类型。
比如有三个input窗口，一个name为n值为n1，第二个和第三个name为hobits，且值为h1,h2
返回 key - string[] = [{key:n1;values:n1}{key:hobits;values:[h1,h2]}];
```

**Request在servlet中进行服务界面跳转的方式**
```
request.getRequestDispatcher("PageName.html").forward(request, response);
```
**在客户端跳转的方式**
```
response.sendRedirect("PageName.html");
```

## HttpServletResponse 的方法

用 ```PrintWriter pw = response.getWriter();```直接在响应中写入html的内容
```
	response.setContentType("text/html");//设置响应格式
	//response.setContentType("text/html; charset=UTF-8");
	// response.setCharacterEncoding("UTF-8");
	PrintWriter pw= response.getWriter();
        pw.println("<h1>Hello Servlet</h1>");
```

客户端有两种跳转
302 表示临时跳转```response.sendRedirect("fail.html");```
301 表示永久性跳转
```
response.setStatus(301);
response.setHeader("Location", "fail.html");
```
## idea 创建的Servlet项目方法
1. 下载servlet的api
2. 下载tomcat并配置端口，和serve.xml的context的路径，保证能加载到web.xml中各种action的映射(web.xml中的映射对应各个action比如/hello, /success而/login.html这种表示是在根目录下，直接访问就行了)
3. 写前端界面post，get的action，然后再后端用requset response响应请求
4. 记住tomcat加载类文件是在web/WEB-INF/classes文件中，如果idea没输出在这就会爆出classNotFound exception
5. idea要用.iml文件配置class文件输出的位置，当然也可以再project struct中设置，不过最好不用要project struct它会自动加production/out的目录在输出的类文件前
6. 将普通java项目切换成web项目，在idea中project struct的facet选项中修改
7. 如果不再本地手动启动tomcat而是在idea中自动部署tomcat，那么一定要在idea配置好tomcat的位置为你已经配好的tomcat目录

