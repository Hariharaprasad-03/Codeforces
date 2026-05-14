import java.util.Arrays;
import java.util.Scanner;

public class KefkandDishes {

    static long[] satisfaction; // Fixed typo
    static long[][] bonus;
    static long[][] dp;
    static int n, m, k;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();

        satisfaction = new long[n];
        bonus = new long[n][n]; // Using 0-indexing
        dp = new long[1 << n][n];

        for (long[] row : dp) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < n; i++) {
            satisfaction[i] = scanner.nextLong();
        }

        for (int i = 0; i < k; i++) {
            int x = scanner.nextInt() - 1; // Adjusted to 0-index
            int y = scanner.nextInt() - 1; // Adjusted to 0-index
            long c = scanner.nextLong();
            bonus[x][y] = c;
        }

        long finalAns = 0;
        // If m=1, the loop below handles it, but if m > 1 we start recursing
        if (m == 1) {
            for (long s : satisfaction) finalAns = Math.max(finalAns, s);
        } else {
            for (int i = 0; i < n; i++) {
                finalAns = Math.max(finalAns, satisfaction[i] + solve(1 << i, i, 1));
            }
        }

        System.out.println(finalAns);
    }

    public static long solve(int mask, int last, int cnt) {
        if (cnt == m) return 0; // Base case is reaching m dishes

        if (dp[mask][last] != -1) return dp[mask][last];

        long res = 0;
        for (int i = 0; i < n; i++) {
            // If dish 'i' has not been eaten yet
            if ((mask & (1 << i)) == 0) {
                long cur = satisfaction[i]
                        + bonus[last][i]
                        + solve(mask | (1 << i), i, cnt + 1);
                res = Math.max(res, cur);
            }
        }
        return dp[mask][last] = res;
    }
}