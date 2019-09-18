import java.util.Random;

public class RandomGenerator {

    public static int[] randomPermutation(int n){
        if(n <= 0)return null;

        Random generator = new Random();

        int[] ans = new int[n];
        for(int i = 0; i < n; i++){
            ans[i] = i;
        }

        for(int i = 0; i < n; i++){
            int j = generator.nextInt(n - i) + i;
            int t = ans[i];
            ans[i] = ans[j];
            ans[j] = t;
        }

        return ans;
    }
}
