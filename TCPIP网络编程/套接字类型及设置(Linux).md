## 套接字协议及其数据传输类型
**创建套接字**
```
#include<sys/socket.h>
int socket(int domain, int type, int protocol);
```
domain 协议族信息
type 套接字数据传输类型信息
protocol 计算机间通信中使用的协议