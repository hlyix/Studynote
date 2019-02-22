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

ipv4（PF_INET）协议族中也存在多种方式，例如TCP和UDP，所以需要我们制定第二个参数。
1. 面向连接的套接字（SOCKET_STREAM）
2. 面向消息的套接字（SOCKET_DGRAM）

SOCKET_STREAM
- 传输过程中数据不会消失（数据丢失重传）
- 按序传输数据
- 传输的数据不存在数据边界BOUNDARY

这里解释一下第三个BOUNDARY，此处的意思就是，read（接收）和write（写出）的次数不一定是一样的，可能一次write完成而read两次完成。

SOCKET_DGRAM
- 强调快速传输而非传输顺序
- 传输的数据可能丢失也可能损毁
- 传输的数据存在数据边界BOUNDARY
- 限制每次传输的数据大小

优点是速度更快，存在边界意味着读和写的次数要相同。

**protocol协议的最终选择**

由于同一个协议族可能有多个数据传输方式相同的协议，所以需要通过第三个参数制定具体的协议信息。
