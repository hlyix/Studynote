
- [实现加减乘除](#%E5%AE%9E%E7%8E%B0%E5%8A%A0%E5%87%8F%E4%B9%98%E9%99%A4)
  - [leetcode43乘法运算](#leetcode43%E4%B9%98%E6%B3%95%E8%BF%90%E7%AE%97)
- [正则表达式](#%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F)
  - [leetcode28题](#leetcode28%E9%A2%98)
  - [leetcode 44题](#leetcode-44%E9%A2%98)
- [枚举类题型总结](#%E6%9E%9A%E4%B8%BE%E7%B1%BB%E9%A2%98%E5%9E%8B%E6%80%BB%E7%BB%93)

# 实现加减乘除
## leetcode43乘法运算 
[原题地址](https://leetcode.com/problems/multiply-strings/)

暴力思路：
1、先解决加法
2、再解决乘法（单数乘以多数）
3、综合

好的思路：
利用乘数与结果对应位置的关系
[详细解法](https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation)
[代码](src/No43.java)


# 正则表达式

## leetcode28题

思路：

1. 首先要确定下一个字符（本字符指针+1）是不是'*'如果是则分两种情况
- 当text本字符与pattern的本字符相等时（text.charAt(t) == pattern.charAt(p)）,分三种情况
  - a* == a则左边指针p+2移动到末尾，右边指针t不动
  - a* == aaa则左边指针p不动，右边指针t+1移动到第二个a
  - a*b == ab则左边指针p+2移动到b，右边指针t+1移动到第二个a

- 当text本字符与pattern的本字符不相等时(text.charAt(t) != pattern.charAt(p)),只有一种情况
  - b*a == a则左边指针p+2，右边指针t不动

2. 如果下一个字符串不是*的话，直接比较本符串是否相等（text.charAt(t) == pattern.charAt(p))

[源代码](src/No28.java)

## leetcode 44题
[源代码](src/No44.java)
[原题地址](https://leetcode.com/problems/wildcard-matching/)

思路：

1. 如果当前字符为'*' 
  - pattern指针+1，text指针+1
  - pattern指针不动，text指针+1
2. 如果当前字符不为'*',如果当前两个字符相等则pattern和text同时+1，否则返回false

![图解1](pic/regular_match1.png)
![图解2](pic/regular_match2.png)


# 枚举类题型总结
[leetcode讨论区](https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning))