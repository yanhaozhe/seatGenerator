import java.util.Random;

public class RandomGenerator {

    public static int[] randomPermutation(int n, int row, int column, boolean isSpecialRuleEnabled, boolean isMixedSeat, int boys, int girls, int[][] allowList, int[][] banList){
        if(!isSpecialRuleEnabled)return randomPermutation(n);

        boolean[][] availableSeat = new boolean[n][n]; // availableSeat[i][j] means whether student i can be offered seat j.
        Random generator = new Random();

        int minV = Math.min(boys, girls);

        if(isMixedSeat) {
            int firstGirl = generator.nextInt(2);

            for (int i = 0; i < n; i++) {
                if (i < girls) {  // The current is girl
                    for (int j = firstGirl; j < 2 * minV; j += 2)
                        availableSeat[i][j] = true;

                    if (girls > boys) {
                        for (int j = minV * 2; j < n; j++)
                            availableSeat[i][j] = true;
                    }
                }
                else {
                    for (int j = 1 - firstGirl; j < 2 * minV; j += 2)
                        availableSeat[i][j] = true;

                    if (girls < boys) {
                        for (int j = minV * 2; j < n; j++)
                            availableSeat[i][j] = true;
                    }
                }
            }
        }

        int[] curSeats = new int[n];
        int nearby;
        int countSeat;

        int[] ans = new int[n];
        int[] phi = new int[n];

        for(int i = 0; i < n; i++){
            countSeat = 0;
            nearby = -1;
            for(int j = 0; j < i; j++){
                if(allowList[i][j] == 1) {
                    nearby = j;
                    break;
                }
            }

            if(nearby > -1){
                int adjIndex = phi[nearby];
                for(int j = 0; j < n; j++)
                    availableSeat[i][j] = false;

                if(((adjIndex + 1) / column) == adjIndex / column){
                    availableSeat[i][adjIndex + 1] = true;
                }

                if(((adjIndex - 1) / column) == adjIndex / column){
                    availableSeat[i][adjIndex - 1] = true;
                }
            }


            for(int j = 0; j < n; j++){
                if(banList[i][j] == 1){
                    availableSeat[i][j] = false;
                }

                else if(availableSeat[i][j]){
                    curSeats[countSeat++] = j;
                }
            }

            if(countSeat == 0)
                return null;

            int kIndex = generator.nextInt(countSeat);
            int seatNum = curSeats[kIndex];
            ans[seatNum] = i;
            phi[i] = seatNum;

            for(int j = i + 1; j < n; j++)
                availableSeat[j][seatNum] = false;
        }

        return ans;
    }

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
