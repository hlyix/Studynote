public class No43 {
    public String multiply(String num1, String num2) {
        if (num1.length() == 0 || num2.length() == 0)
            return null;
        if (num1 == null || num2 == null)
            return null;
        if (num1.equals("0") || num2.equals("0"))
            return "0";

        if (num1.length() < num2.length()) {
            String tmp = num1;
            num1 = num2;
            num2 = tmp;
        }
        char[] c1 = num1.toCharArray();
        char[] c2 = num2.toCharArray();
        String res = "0";
        for (int i = c2.length - 1; i >= 0; i--) {
            String next = multiplySingle(c1, c2[i]);
            for (int j = c2.length - 1; j > i; j--)
                next = next + '0';
            char[] tmp1 = next.toCharArray();
            char[] tmp2 = res.toCharArray();
            res = add(tmp1, tmp2);
        }


        return res;
    }


    public String multiplySingle(char[] c1, char c2) {
        String res = "";
        int incre = 0;
        for (int i = c1.length - 1; i >= 0; i--) {
            int sum = (c1[i] - '0') * (c2 - '0') + incre;
            if (sum >= 10) {
                res = Integer.toString(sum % 10) + res;
                incre = sum / 10;
            } else {
                res = Integer.toString(sum) + res;
                incre = 0;
            }
        }
        if (incre != 0)
            res = Integer.toString(incre) + res;
        return res;
    }


    public String add(char[] c1, char[] c2) {
        String res = "";
        int incre = 0;
        int gap = c1.length - c2.length;
        for (int i = c2.length - 1; i >= 0; i--) {
            int sum = (c1[i + gap] - '0') + (c2[i] - '0') + incre;
            if (sum >= 10) {
                int a = sum - 10;
                res = (Integer.toString(a) + res);
                incre = 1;
            } else {
                res = Integer.toString(sum) + res;
                incre = 0;
            }
        }
        for (int i = gap - 1; i >= 0; i--) {
            int sum = c1[i] - '0';
            if (incre == 1)
                sum = (c1[i] - '0') + incre;
            if (sum >= 10) {
                res = "0" + res;
                incre = 1;
            } else {
                res = Integer.toString(sum) + res;
                incre = 0;
            }
        }
        if (incre == 1)
            res = "1" + res;
        return res;
    }

    //简单方法
    public String multiplySimpleVersion(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        int[] pos = new int[m + n];
        for (int i = num1.length() - 1; i >= 0; i--)
            for (int j = num2.length() - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2];
                pos[p2] = sum % 10;
                pos[p1] += sum / 10;
            }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pos.length; i++) {
            if (sb.length() == 0 && pos[i] == 0)
                continue;
            sb.append(pos[i]);
        }
        return sb.length() == 0 ? "0": sb.toString();
    }


    public static void main(String[] args) {
        String t1 = "9812";
        String t2 = "439";
        No43 test = new No43();
        System.out.println(test.multiplySimpleVersion(t1,t2));


    }
}
