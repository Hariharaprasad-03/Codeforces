import java.util.*;

public class ValuesCanYouMake {
    // 3D Memoization table: [coin index][target k][target x]
    static Boolean[][][] memo;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < n; i++) {
            coins[i] = sc.nextInt();
        }

        // Initialize memo with constraints from image_873791.png
//        memo = new Boolean[n][k + 1][k + 1];

        List<Integer> result = new ArrayList<>();

        // Iterate through every possible value Arya could make (0 to k)
        /*for (int x = 0; x <= k; x++) {
            if (canForm(n - 1, k, x, coins)) {
                result.add(x);
            }
        }

        // Output format per image_873791.png
        System.out.println(result.size());
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + (i == result.size() - 1 ? "" : " "));
        }*/


        boolean[][] dp = new boolean[k + 1][k + 1];
        dp[0][0] = true;

        for (int coin : coins) {
            for (int j = k; j >= coin; j--) {
                for (int l = j; l >= 0; l--) {

                    if (dp[j - coin][l]) {
                        dp[j][l] = true;
                    }
                    if (l >= coin && dp[j - coin][l - coin]) {
                        dp[j][l] = true;
                    }
                }
            }
        }

        for (int i = 0; i <= k; i++) {
            if (dp[k][i]) {
                result.add(i);
            }
        }

        System.out.println(result.size());
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + (i == result.size() - 1 ? "" : " "));
        }
    }

        public static boolean canForm ( int idx, int remK, int remX, int[] coins){
            // Base Case: If the total target k is met, check if Arya's sub-target x is also met
            if (remK == 0) return remX == 0;

            // Base Case: No more coins or target becomes impossible
            if (idx < 0 || remK < 0 || remX < 0) return false;
            // Memoization check
            if (memo[idx][remK][remX] != null) return memo[idx][remK][remX];
            // 1. Skip the coin entirely
            boolean res = canForm(idx - 1, remK, remX, coins);

            // 2. Give coin to Arya, but Arya doesn't use it for x
            if (!res && remK >= coins[idx]) {
                res = canForm(idx - 1, remK - coins[idx], remX, coins);
            }
            // 3. Give coin to Arya, and Arya uses it for x
            if (!res && remK >= coins[idx] && remX >= coins[idx]) {
                res = canForm(idx - 1, remK - coins[idx], remX - coins[idx], coins);
            }
            return memo[idx][remK][remX] = res;
        }
}
