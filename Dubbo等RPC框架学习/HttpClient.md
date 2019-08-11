# 1. HttpClient

httpclient quick start的官网：http://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html

关于http请求头的汇总：http://tool.oschina.net/commons/

```java
//创建client工具
CloseableHttpClient httpclient = HttpClients.createDefault();
//设置GET方法的
HttpGet httpGet = new HttpGet("http://targethost/homepage");
CloseableHttpResponse response1 = httpclient.execute(httpGet);

try {
    //打印服务端响应状态
    System.out.println(response1.getStatusLine());
    //获取相应的实体内容
    HttpEntity entity1 = response1.getEntity();
    //该方法获取entity的内容，返回一个inputStream对象，需要我们调用Read方法读取到内存中，比如byte[]中
    EntityUtils.consume(entity1);
} finally {
    //关闭连接
    response1.close();
}

//设置post方法
HttpPost httpPost = new HttpPost("http://targethost/login");
//设置键值对list
List <NameValuePair> nvps = new ArrayList <NameValuePair>();
nvps.add(new BasicNameValuePair("username", "vip"));
nvps.add(new BasicNameValuePair("password", "secret"));
//设置提交给Post,就是普通表单的entity
httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//执行http网络请求
CloseableHttpResponse response2 = httpclient.execute(httpPost);

try {
    System.out.println(response2.getStatusLine());
    HttpEntity entity2 = response2.getEntity();
    // 返回inputStream
    EntityUtils.consume(entity2);
    //返回entity2为字符串
    System.out.println(EntityUtils.toString(entity2, "UTF-8"));
} finally {
    response2.close();
}
```

当然第二种方法更简单

```java

Request.Get("http://targethost/homepage")
    .execute().returnContent();
Request.Post("http://targethost/login")
    .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
    .execute().returnContent();
```

默认的```content-type```是```text/html; charset=utf-8```

如果要设置header，可以用

```java
httpPost.setHeader("Content-type", contentType);//设置文本的格式
```

服务器端返回的header设置，可以在注解内实现，例如

```java
    @RequestMapping(value = "/getService",produces = "application/octet-stream")
    @ResponseBody
```

使用Json传输

```java
 //        json方式提交
         JSONObject jsonParam = new JSONObject();  
         jsonParam.put("name", "admin");
         jsonParam.put("pass", "123456");
         StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
         entity.setContentEncoding("UTF-8"); 
         entity.setContentType("application/json");    //这也是一种设置方法
         httpPost.setEntity(entity);

//         返回的json处理
		HttpResponse resp = client.execute(httpPost);
         HttpEntity he = resp.getEntity();
         String respContent = EntityUtils.toString(he,"UTF-8");
```

使用二进制传输

```java

        HttpPost httpPost = new HttpPost("http:localhost:8080");
		//设置为二进制格式
        httpPost.setHeader("Content-type", "application/octet-stream");

		//提交post请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

		//解析返回的二进制entity
        try {
            HttpEntity entityResponse = httpResponse.getEntity();
            //获取entity长度
            int contentLength = (int) entityResponse.getContentLength();
            //小于0则获取失败
            if (contentLength <= 0)
                throw new IOException("No response");
            //字节数组要等于获取的二进制长度
            byte[] respBuffer = new byte[contentLength];
			//判断语句中执行了inputstream.read方法获取二进制字节到buffer中
            if (entityResponse.getContent().read(respBuffer) >= respBuffer.length)
                throw new IOException("Read response buffer error");
            return respBuffer;
        } finally {
            httpResponse.close();
        }

```





