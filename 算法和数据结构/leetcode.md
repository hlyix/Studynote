# leetcode 43
43. Multiply Strings(实现乘法)
Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.

**Example 1:**
> Input: num1 = "2", num2 = "3"
Output: "6"

**Example 2:**
> Input: num1 = "123", num2 = "456"
Output: "56088"
Note:
- The length of both num1 and num2 is < 110.
- Both num1 and num2 contain only digits 0-9.
- Both num1 and num2 do not contain any leading zero, except the number 0 itself.
- You must not use any built-in BigInteger library or convert the inputs to integer directly.

暴力思路：
1、先解决加法
2、再解决乘法（单数乘以多数）
3、综合

好的思路：
利用乘数与结果对应位置的关系
[详细解法](https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation)
[代码](src/No43.java)

