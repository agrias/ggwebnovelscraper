import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ramza on 7/16/2016.
 */
public class DefaultDemo {

    public static String title;
    public static String author;
    public static String extension;
    public static String resources_directory;
    public static String directory;

    public static void main(String[] args) throws IOException, InterruptedException {

        directory = "C:\\dev\\webnovels\\";
        getAndWriteISSTH();

//        String story = getStoryHTML("http://www.wuxiaworld.com/issth-index/issth-book-6-chapter-1000/");
//        String total = GgUtils.executePost("http://www.wuxiaworld.com/issth-index/issth-book-6-chapter-1000/");
//
//        Arrays.stream(story
//                .split("\n")).forEach((line) -> System.out.println(line));
    }

    public static void getAndWriteISSTH() throws IOException, InterruptedException {

        title = "I Shall Seal The Heavens";
        author = "Er Gen";
        extension = ".html";

        resources_directory = "C:\\Users\\radgrias\\IdeaProjects\\ggwebnovelscraper\\Utilities\\src\\main\\resources";

        //

        String filename = resources_directory+"\\"+"parse_ISSTHTOC.html";
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String curr;
        int i = 1;

        long beginning = System.currentTimeMillis();

        class QuickWriteTask implements Callable{

            String filename;
            String content;

            QuickWriteTask(String filename, String content){
                this.filename = filename;
                this.content = content;
            }

            @Override
            public Object call() throws Exception {
                String story = getStoryHTML(content);
                GgUtils.writeToFile(story, directory, filename);
                //System.out.println("Multi-threaded write done.");
                return "Done";
            }
        }

        ArrayList<QuickWriteTask> list = new ArrayList();

        while ((curr = br.readLine()) != null){
            Formatter format = new Formatter();
            // this needs to be done
            String story = curr;
            String filename2 = format.format("%04d", i).toString() + " " + title + " - " + author + extension;
            
            list.add(new QuickWriteTask(filename2, story));
            //GgUtils.writeToFile(story, directory, filename2);
            i++;
            System.out.println("test"+i);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        list.stream().forEach(executor::submit);

        Thread.sleep(3000);

        executor.notifyAll();
        executor.shutdown();

        long ending = System.currentTimeMillis();

        System.out.println(Double.valueOf(ending-beginning)/1000);
    }

    public static void getAndWriteCoilingDragon() throws IOException {

        title = "Coiling Dragon";
        author = "I Eat Tomatoes";
        extension = ".html";

        resources_directory = "G:\\dev\\code\\Utilities\\src\\main\\resources";

        //

        String filename = resources_directory+"\\"+"parse_CDTOC.html";
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String curr;
        int i = 1;

        long beginning = System.currentTimeMillis();

        while ((curr = br.readLine()) != null){
            Formatter format = new Formatter();
            String story = getStoryHTML(curr);
            String filename2 = format.format("%03d", i).toString() + " " + title + " - " + author + extension;
            GgUtils.writeToFile(story, directory, filename2);
            i++;
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        long ending = System.currentTimeMillis();

        System.out.println(Double.valueOf(ending-beginning)/1000);
    }

    public static void quickerWrite(){
        Formatter format = new Formatter();
        String story = getStoryHTML("");
        //String filename2 = format.format("%03d", i).toString() + " " + title + " - " + author + extension;
        //GgUtils.writeToFile(story, directory, filename2);
    }

    public static String getStoryHTML(String url){

        String content = null;
        String header = "<!DOCTYPE html>\n<html xmlns=\"http://www.w3.org/1999/xhtml\" prefix=\"\" lang=\"en-US\">\n";
        String footer = "\n</html>";

        try {
            content = GgUtils.decodeNumericEntities(GgUtils.executePost(url));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String begStr = "<div itemprop=\"articleBody\">";
        String endStr = "</div>";

        //String title = (titleindex == -1)||(titleindex2 == -1) ? "" : content.substring(titleindex, titleindex2+8)+"\n";

        //String begStr = "<div class='cover'>";
        //String endStr = "<div class='post-footer'>";

        content = header+content.substring(content.indexOf(begStr));

        //fix for spoiler titles
        if (content.contains("Click to show")) {
            System.out.println("Found spoiler title!");
            content = content.substring(0, content.indexOf(endStr, content.indexOf(endStr)+1) + endStr.length()) + footer;
        }else
            content = content.substring(0, content.indexOf(endStr))+footer;

        String regex = "<hr/>";
        Pattern scriptPattern = Pattern.compile(regex);
        Matcher matcher = scriptPattern.matcher(content);
        content = matcher.replaceAll("<hr>");

        List<String> lines = Arrays.asList(content.split("\n"));
        StringBuilder output = new StringBuilder();
        lines.stream().filter((x) -> !x.contains("href")).forEach((x) -> output.append(x).append("\n"));

        return output.toString();
    }
}
