# 一、java8简介
主要包括三个方面：
1. Stream API（流处理）
2. 向方法传递代码技巧（lambda表达式）
3. 接口中的默认方法

### 1、流处理
这一思想是有些像流水线上的工人，虽然处理的是不同阶段的任务，但却是在同时并向处理。
基于这一思想，java8在java.util.stream中添加了一个Stream API。它可以将很多方法链路起来形成一个复杂的流水线。
这种做法的关键在于：可以透明的把输入的不相关部分，分别拿到几个CPU内核上区分别执行Stream操作流水线————几乎是免费的并行处理，用不着费劲弄Thread。

### 2、用行为参数化把代码传递给方法（lambda）
由于享受了免费的并行处理，当然也会由缺点，例如：必须能够同时对不同的输入**安全的执行**，也就是lambda这些函数不能访问共享的可变数据。

几个例子说明（农夫选苹果，颜色是绿色，大于150g）：
传统写法：
`选绿色`
```java
public static List<Apple> filterGreenApples(List<Apple> inventory){
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory){
       if("green".equals(apple.getColor())){
               result.add(apple);
            }
       }
       return result;
}
```
`大于`150g
```java
	public static List<Apple> filterHeavyApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple appple : inventory) {
			if (apple.getWeight() > 150) {
				result.add(apple);
			}
		}
		return result;
	}

```

但是前面写太繁琐，我们试着用接口来判别，再用把判别的接口方法作为参数放入filter方法中，此处要用到泛型
最重要的便是那泛型接口
```java
	public static boolean isGreenApple(Apple apple) {
		return "green".equals(apple.getColor());
	}
	
	public static boolean isHeavyApple(Apple apple) {
		return apple.getWeight() > 150;
	}
	
	public interface Predicate<T>{//相当于谓语动词
		boolean test(T t);
	}
	
	static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){//此处可把接口方法作为一个函数参数！
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory) {
			if(p.test(apple)) {
				result.add(apple);
			}
		}
	}
```
具体如何使用上面函数？如下
```java
   filterApples( inventory, Apple :: isGreenApple);
//Apple表名他是inventory中的一个Apple实例，后面isGreenApple就相当于实现接口内的boolean test，传递给fileterapple的if语句中
或者
   filterApples( inventory, Apple :: isHeavyApple);
```

当然也可使用lambda函数
```java

  filterApples(inventory, (Apple a)-> "green".equals(a.getColor()));
//或者
  filterApples(inventory, (Apple a)-> a.getWeight() > 150);
//或者
  filterApples(inventory, (Apple a)-> a.getWeight() < 8 || "brown".equals(a.getcolor()))

```

### 关于流处理的函数
此处例子是：
从一个transactions列表中找出交易额较高的交易，然后按货币种类分组。
```java
	Map<Currency, List<Transaction>> transactionByCurrencies = new HashMap<>();
	for(Transaction transaction : transactions) {
		if(transaction.getPrice() > 1000) {
			Currency currency = transaction.getCurrency();
			List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
			if(transactionsForCurrency == null) {
				transactionsForCurrency = new ArrayList<>();
				transactionsByCurrencies.put(currency,transactionsForCurrency);
				
			}
			transactionsForCurrency.add(transaction);
```

但是用流中函数写就很方便
```java
Map<Currency,List<Transaction>> transactionsByCurrencies =
 transactions.stream().filter((Transaction t)-> t.getprice() > 1000).collect(groupingBy(Transaction :: getCurrency));
```

### 流中多线程API
顺序处理
```java
 import static java.util.stream.Collections.toList;
 List<Apple> heavyApples = inventory.stream().filter((Apple) -> a.getWeight() > 150).collection(toList());
```

并行处理
```java
 import static java.util.stream.Collections.toList;
 List<Apple> heavyApples = inventory.parallelStream().filter((Apple) -> a.getWeight() > 150).collection(toList());
```
![picture](pic/1.jpg)


### 三、默认方法
default，意思是每个接口有其默认的方法，实现接口时无需每个方法一一实现