import java.util.Scanner;

public class woodCuttors { // Changed to Main for Codeforces compatibility

    public static int[] x;
    public static int[] h;
    public static int[][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        x = new int[n];
        h = new int[n];
        dp = new int[n][3];

        for (int i = 0; i < n; i++) {
            x[i] = sc.nextInt();
            h[i] = sc.nextInt();
        }

        // Quick check for n=1 to avoid array crashes
        if (n == 1) {
            System.out.println(1);
            return;
        }

        computeDp(n);

        int ans = 0;
        // The answer is the best outcome after processing all trees
        for (int state = 0; state < 3; state++) {
            ans = Math.max(ans, dp[n - 1][state]);
        }

        System.out.println(ans);
    }

    public static void computeDp(int n) {
        // State 0: Fall Left, State 1: Stay, State 2: Fall Right

        // Initialize Tree 0
        dp[0][0] = 1; // Can always fall left
        dp[0][1] = 1; // Staying is always valid
        // Check x[1] safely because we handled n=1 in main
        dp[0][2] = (x[0] + h[0] < x[1]) ? 1 : 0;

        for (int i = 1; i < n; i++) {
            // 1. Current tree stays standing (Always possible)
            dp[i][1] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][1], dp[i - 1][2]));

            // 2. Current tree falls left
            if (x[i] - h[i] > x[i - 1]) {
                // If prev fell left or stayed, it ends at x[i-1]
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]) + 1;

                // If prev fell right, it ends at x[i-1] + h[i-1]
                if (x[i] - h[i] > x[i - 1] + h[i - 1]) {
                    dp[i][0] = Math.max(dp[i][0], dp[i - 1][2] + 1);
                }
            }

            // 3. Current tree falls right
            // For the last tree, it's always possible. For others, check x[i+1].
            if (i == n - 1 || x[i] + h[i] < x[i + 1]) {
                dp[i][2] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][1], dp[i - 1][2])) + 1;
            }
        }
    }
}