import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class parser {

    public static void main (String[] args) {

        Methods methods = new Methods();
        String rawHTML = methods.getHTMLSource("https://www.facebook.com/groups/guitarexchange");

        Pattern descPattern = Pattern.compile("(<span>)(.*)(/span>)");

        Document doc = Jsoup.parse(rawHTML);

        Elements descriptions = doc.select("div[class*='_5pbx']");
        Elements titles = doc.select("div[class='_l53']");
        Elements prices = doc.select("div[class='_l57']");
        Elements locations = doc.select("div[class='_l58']");
        Elements urls = doc.select("div[id*='feed_subtitle']");


        for (int i=0; i<10; i++) {
            System.out.println(descriptions.get(i).text());
            System.out.println(titles.get(i).text());
            System.out.println(prices.get(i).text());
            System.out.println(locations.get(i).text());
            System.out.println(urls.get(i).text());
            System.out.println("\n");
        }

        /*
        System.out.println(descriptions);
        System.out.println(titles);
        System.out.println(prices);
        System.out.println(locations);
        System.out.println(urls);
        */

    }
}
