package src.solutions;

public class BruteForce implements Solution {
    @Override
    public String solve(String s) {
        if(s.length() < 2) {
            return s;
        }
        String max = "";
        for (int i = 0, length = s.length()+1; i < length; i++) {
            for (int j = i+1; j < length; j++) {
                if(max.length() < s.substring(i, j).length() && isPalindrome(s.substring(i, j))) {
                    max = s.substring(i, j);
                }
            }
        }
        return max;
    }

    private boolean isPalindrome(String s) {
        if(s.length() < 2) {
            return true;
        }
        char[] c = s.toCharArray();
        for(int i=0, halfLength = c.length/2; i < halfLength; i++) {
            if(c[i] != c[c.length-i-1]) {
                return false;
            }
        }
        return true;
    }
}
