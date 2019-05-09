public class No44 {

    int[][] memo;

    public boolean isMatch(String s, String p) {
        memo = new int[s.length() + 1][p.length() + 1];
        return isMatch(s, p, 0, 0);
    }

    public boolean isMatch(String s, String p, int ps, int pp) {
        if (memo[ps][pp] != 0)
            return memo[ps][pp] == 1 ? true:false;

        if (p.isEmpty()) {
            memo[ps][pp] = s.isEmpty() ? 1 : -1;
            return s.isEmpty();
        }

        if (!s.isEmpty()) {
            if (p.charAt(0) == '*') {
                memo[ps][pp] = isMatch(s.substring(1), p, ps + 1, pp) || isMatch(s, p.substring(1), ps, pp + 1) || isMatch(s.substring(1), p.substring(1), ps + 1, pp + 1)
                        ? 1 : -1;
                return memo[ps][pp] == 1 ? true : false;
            }

            if ((p.charAt(0) == s.charAt(0)) || p.charAt(0) == '?') {
                memo[ps][pp] = isMatch(s.substring(1), p.substring(1), ps + 1, pp + 1) ? 1 : -1;
                return memo[ps][pp] == 1 ? true : false;
            }
        }

        if (s.isEmpty() && p.charAt(0) == '*') {
            memo[ps][pp] = isMatch(s, p.substring(1), ps, pp + 1) ? 1 : -1;
            return memo[ps][pp] == 1 ? true : false;
        }

        return false;
    }

    public static void main(String[] args) {
        No44 test = new No44();
        System.out.println(test.isMatch("aa", "a***"));
    }

}
