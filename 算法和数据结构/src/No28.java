public class No28 {

    public boolean isMatch(String text, String pattern) {

        return isMatch(text, pattern, 0, 0);

    }

    public boolean isMatch(String text, String pattern, int t, int p) {
        if (t == text.length() && p == pattern.length())
            return true;

        if (t != text.length() && p == pattern.length())
            return false;

        if (p + 1 < pattern.length())
            if (pattern.charAt(p + 1) == '*') {
                if (t < text.length() && (pattern.charAt(p) == text.charAt(t) || pattern.charAt(p) == '.'))//t<text.length()是为了防止a == ab*, text到了而pattern每到。
                    return isMatch(text, pattern, t + 1, p) || isMatch(text, pattern, t, p + 2)
                            || isMatch(text, pattern, t + 1, p + 2);//a* == a, a*b == ab, a*b == aab
                else return isMatch(text, pattern, t, p + 2); //a*b == b
            }

        if (p < pattern.length() && t < text.length())
            if (text.charAt(t) == pattern.charAt(p) || pattern.charAt(p) == '.')
                return isMatch(text, pattern, t + 1, p + 1);

        return false;

    }

    //标准答案
    public boolean ismatch(String text, String pattern) {
        if (pattern.isEmpty()) return text.isEmpty();

        boolean firstMatch = (!text.isEmpty() && ((pattern.charAt(0) == text.charAt(0)) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            if (firstMatch)
                return ismatch(text.substring(1), pattern);
            //至于这里为什么可以删除两个比较，这是由于边界条件改变了（a* == a）text越界而pattern没有的情况不用讨论。
            //第二个时由于a*b==ab这个可以右边指针+1而左边不变，到下一个循环的时候这里就是a*b == b，就是firstmatch为false的情况。
            else
                return (ismatch(text, pattern.substring(2)));
        }else return firstMatch && ismatch(text.substring(1),pattern.substring(1));
    }

    public static void main(String[] args) {

        No28 test = new No28();
        System.out.println(test.isMatch("a", "ab*"));
        System.out.println(test.ismatch("a", "ab*"));
    }

}
