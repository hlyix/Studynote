#### rstrip()和strip() 
Python能够找出字符串开头和末尾多余的空白。要确保字符串末尾没有空白，可使用方法rstrip() 。 
```python
❶ >>> favorite_language = 'python '
❷ >>> favorite_language  
'python '
❸ >>> favorite_language.rstrip()  
'python'
```
你还可以剔除字符串开头的空白，或同时剔除字符串两端的空白。为此，可分别使用方法lstrip() 和strip() ： 
```python
>>> favorite_language = ' python '  
>>> favorite_language.rstrip()  
' python'
>>> favorite_language.lstrip()  
'python '  
>>> favorite_language.strip()  
'python'
```
#### str()

可调用函数str() ， 它让Python将非字符串值表示为字符串：
```python
age = 23
message = "Happy " + str(age) + "rd Birthday!" 
print(message)
```

#### title
title(str)把字符串里每个单词的首字母大写

###列表

```
motocycle = ['honda','yamaha','suzuki']

```

**pop()**
删除末尾元素
或者
pop(1)删除第二个元素


**del(index)删除index处的元素**

**remove(value)直接删除值**
**注意**： 方法remove() 只删除第一个指定的值。如果要删除的值可能在列表中出现多次，就需要使用循环来判断是否删除了所有这样的值。你将在第7章学习如何这 样做。
例如[ab,ab,ab,a,a,c]
remove(ab),那么指挥remove第一个ab，其中还有两个ab没被remove

## 列表

### 1 组织列表

####  sort和sorted方法：

按首字母顺序排序列表

sort(reverse = true)逆序排序

sort是永久排序，改变列表中的顺序
sorted是临时排序，只当前显示排序顺序，列表内元素不会改变

#### reverse

反转列表```list.reverse()```

#### len

len(list) 相当于java的list.size()

#### range

可以用于创建列表

```python
numbers = list.(rang(1,6))
//打印结果是[1,2,3,4,5]
numbers = list.(range(2,11,2))
//打印结果是[2,4,6,8,10]
```

#### 列表切片

```python
players = ['charles', 'martina', 'michael', 'florence', 'eli']
print(players[1:4])
//输出结果['martina', 'michael', 'florence']
print(players[:4])
//输出结果['charles', 'martina', 'michael', 'florence']
print(players[-3:])//输出最后三位
print(players[2:])//输出第二个选手一直到末尾
```

#### 复制列表

```python
foods = ['pizza', 'falafel', 'carrot cake'] 
//复制,类似于java的clone
foods01 = foods[:]
//指针指向foods而不是复制
foods02 = foods

```



**访问最后 一个元素可以用list[-1]**

### 2 遍历列表

```python
magicians = ['alice', 'david', 'carolina'] 
for magician in magicians:
	print(magician)
```

### 3 列表的基本操作

- 增加```list.append(element)```
- 删除```del list[index]```或者 ```list.pop()```弹出末尾元素，```list.remove(element)```根据值删除
- 插入```list.insert(index,element)```