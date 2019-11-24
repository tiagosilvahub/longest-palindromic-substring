package src.solutions;

public class ManachersAlgorithm implements Solution {
    @Override
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
}
