给日期格式化：

```java
1 @Test
2     public void  name()
3     {
4         Calendar calendar=Calendar.getInstance();
5         SimpleDateFormat smft=new SimpleDateFormat("YYYY年MM月dd日  HH:mm:ss E");
6         String nowString=smft.format(calendar.getTime());
7         System.out.println(nowString);
8     }

```

