# longest-palindromic-substring
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

https://leetcode.com/problems/longest-palindromic-substring/

Example 1:
```
Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
```
Example 2:
```
Input: "cbbd"
Output: "bb"
```

Brute force solution, for every possible substring, check if it is a palindrome. O(n^3)
```
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
```

Instead of thinking in terms of were a palindrome can start and finish, we can consider it's center. 

The center of a palindrome can be in a letter, or between two letters. There are 2*n - 1 possible centers

For each of them, we can expand while we see the same character on both sides.

If the length of the current found palindrome is the biggest we have found so far, we save the left and right positions.

Reaching the solution in O(n^2) time

```
public String solve(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```


Manacher's Algorithm implementation:
O(n) in time

```
public String solve(String s) {
    int sLength = s.length();
    String oddString = getOddifiedString(s, sLength);
    int length = (2 * sLength) + 1;
    int[] LPS = new int[length];
    int centre = 0;
    int rightBoundary = 0;
    int maxLen = 0;
    int maxI = 0;
    for(int i = 0; i < length; i++) {
        int mirror = (2 * centre) - i;
        if(i < rightBoundary) {
            LPS[i] = Math.min(rightBoundary - i, LPS[mirror]);
        }
        int a = i + (1 + LPS[i]);
        int b = i - (1 + LPS[i]);
        while(a < length && b >= 0 && oddString.charAt(a) == oddString.charAt(b)) {
            LPS[i]++;
            a++;
            b--;
        }
        if(i + LPS[i] > rightBoundary) {
            centre = i;
            rightBoundary = i + LPS[i];
            if(LPS[i] > maxLen) {
                maxLen = LPS[i];
                maxI = i;
            }
        }
    }
    return s.substring( (maxI - LPS[maxI])/2 , (maxI + LPS[maxI])/2 );
}

private String getOddifiedString(String s, int N){
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < N; i++){
        sb.append("#");
        sb.append(s.charAt(i));
    }
    sb.append("#");
    return sb.toString();
}
```
