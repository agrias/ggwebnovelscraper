import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by ramza on 7/16/2016.
 */
public class Functions {

    public static String getStoryHTMLusingParameters(String head, String foot, String beg, String end, Predicate filter, String url){

        String header = head;
        String footer = foot;

        String content = null;
        String begStr = beg;
        String endStr = end;

        try {
            content = GgUtils.decodeNumericEntities(GgUtils.executePost(url));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int titleindex = content.indexOf("<title>");
        int titleindex2 = content.indexOf("</title>");
        String title = (titleindex == -1)||(titleindex2 == -1) ? "" : content.substring(titleindex, titleindex2+8)+"\n";

        content = header+title+content.substring(content.indexOf(begStr));
        content = content.substring(0, content.indexOf(endStr)+endStr.length())+footer;

        List<String> lines = Arrays.asList(content.split("\n"));
        StringBuilder output = new StringBuilder();
        lines.stream().filter(filter).forEach((x) -> output.append(x).append("\n"));

        return output.toString();
    }

}
