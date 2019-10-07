import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static List<String> readFileByLines(File fp) {
        List<String> res = new ArrayList<String>();

        if(!fp.exists()) return null;

        try {

            Scanner sc = new Scanner(fp, "utf-8");

            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                res.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static int[][] readRules(File fp, int n){
        if(fp == null || !fp.exists()){
            System.out.println("Cannot read file " + fp.getName());
            return null;
        }

        int[][] ans = new int[n][n];

        try{
            Scanner sc = new Scanner(fp);

            while(sc.hasNextInt()) {
                int a, b;

                a = sc.nextInt();
                if (sc.hasNextInt())
                    b = sc.nextInt();
                else break;

                ans[a][b] = ans[b][a] = 1;
            }
        }

        catch(Exception ex){
            ex.printStackTrace();
        }

        finally {
            System.out.println("OK");
        }

        return ans;
    }
}
