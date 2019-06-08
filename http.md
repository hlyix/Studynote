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
**Request对象的常用方法：**