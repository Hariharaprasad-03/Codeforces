import java.util.*;

public class ColorfulBricks {
    static final long MOD = 998244353L;

    long [][] dp  ;

    static long modPow(long a, long e) {
        long res = 1;
        a %= MOD;
        while (e > 0) {
            if ((e & 1) == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            e >>= 1;
        }
        return res;
    }

    static long[] fact, invFact;

    static void buildFactorials(int n) {
        fact = new long[n + 1];
        invFact = new long[n + 1];

        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }

        invFact[n] = modPow(fact[n], MOD - 2);
        for (int i = n - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }
    }

    static long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        return fact[n] * invFact[r] % MOD * invFact[n - r] % MOD;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        buildFactorials(n);

        long ans = nCr(n - 1, k);
        ans = ans * m % MOD;
        ans = ans * modPow(m - 1, k) % MOD;

        System.out.println(ans);
    }

    public void solve(int n , int k,int m) {

        dp = new long[n+1][k+1];

        dp[1][0] = m ;

        // i represnts brick j represents k (needed diff color bricks)

        for( int i =2 ; i<=n ; i++) {
            for ( int j =0 ; j<=k ;j++){
                // USING SAME color before
                dp[i][j] = dp[i-1][j];
                //USING DIFFRENT COLOR
                if ( j >0) {
                    dp[i][j] = (dp[i][j] + dp[i-1][j-1] * (m-1))%MOD;
                }
            }
        }

    }
    public long solveMem( int n , int k ,int m){

        if (n == 1) {
            return (k==0)? m : 0 ;
        }

        if (k == 0) return 0 ;

        if (dp[n][k] != -1)return dp[n][k];

        long same = solveMem(n-1,k,m);
        long diff = solveMem(n-1,k-1,m)*(m-1);

        return dp[n][k] = (same+diff)%MOD;

    }
}