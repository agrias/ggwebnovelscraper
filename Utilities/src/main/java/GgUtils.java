import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ramza on 7/16/2016.
 */
public class GgUtils {

    /**
     * Keep the match of a regex
     * @param s string to match on
     * @param pattern to match
     * @return
     */
    public static String match(String s, String pattern){

        String out = "";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);

        while(m.find()){
            out = out.concat(m.group());
        }

        return out;
    }

    /**
     * File Writer using BufferedWriter
     * @param s to write
     * @param d to write to
     * @param f of file
     */
    public static void writeToFile(String s, String d, String f){

        try {

            String content = s;

            File file = new File(d+f);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute a POST on a URL to get the source code
     * @param targetURL
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String executePost(String targetURL)
            throws MalformedURLException, IOException {

        String url = targetURL;
        URLConnection connection = new URL(url).openConnection();

        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

        InputStream response = connection.getInputStream();

        String encoding = connection.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;

        String body = IOUtils.toString(response, encoding);

        return (body);
    }

    /**
     * Takes a String (source html) and cleans the encoding
     * @param s
     * @return
     */
    public static String decodeNumericEntities(String s) {
        StringBuffer sb = new StringBuffer();

        Matcher m = Pattern.compile("\\&#(\\d+);").matcher(s);
        while (m.find()) {
            int uc = Integer.parseInt(m.group(1));
            m.appendReplacement(sb, "");
            sb.appendCodePoint(uc);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
