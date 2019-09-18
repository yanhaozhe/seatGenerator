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
}
