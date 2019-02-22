## 套接字协议及其数据传输类型
**创建套接字**
```
#include<sys/socket.h>
int socket(int domain, int type, int protocol);
```
- domain 协议族信息
- type 套接字数据传输类型信息
- protocol 计算机间通信中使用的协议信息

**domain 协议族信息(protocol family)**
|名称|协议族|
|-|-|
|PF_INET|IPv4互联网协议族|
|PF_INET6|IPv6互联网协议族|
|PF_LOCAL|本地通信的UNIX协议族|
|PF_PACKET|底层套接字协议族|
|PF_IPX|IPX Novell协议族|


**Type 套接字类型**
ipv4（PF_INET）协议族中也存在多种方式，例如TCP和UDP
