public class TextComparator {

    // 计算最长公共子序列长度（空间优化版）
    private static int longestCommonSubsequence(String text1, String text2) {
        if (text1.isEmpty() || text2.isEmpty()) return 0;

        int m = text1.length();
        int n = text2.length();

        // 确保text2是较短的以节省空间
        if (m < n) {
            return longestCommonSubsequence(text2, text1);
        }

        int[] dp = new int[n + 1];
        for (int i = 1; i <= m; i++) {
            int prev = 0;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[j] = prev + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
            }
        }
        return dp[n];
    }

    // 计算文本相似度
    public static double calculateSimilarity(String text1, String text2) {
        int lcsLength = longestCommonSubsequence(text1, text2);
        int len1 = text1.length();
        int len2 = text2.length();

        if (len1 + len2 == 0) {
            return 0.0;
        }
        return (2.0 * lcsLength) / (len1 + len2);
    }
}