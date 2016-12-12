import java.io.*;
import java.util.stream.IntStream;

/**
 * Created by ramza on 7/23/2016.
 */
public class sandbox {
    public static void main(String[] args) throws IOException {

        //int[] scores = {1, 2, 3};
        //int sum = IntStream.of(scores).reduce(0, (a, b) -> a+b);
        //System.out.println(sum);

        String file = "G:\\dev\\code\\Utilities\\src\\main\\resources\\parse_CDTOC.html";

        File temp = new File(file);

        BufferedReader br = new BufferedReader(new FileReader(temp));

        String curr;

        while((curr = br.readLine()) != null){
            System.out.println(curr);
        }

    }
}
