import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class loginTest {

    public static void main(String[] args) {

        Methods methods = new Methods();
        Document group = methods.login("gr3gl@hotmail.com",
                "Bobfriend1",
                "https://www.facebook.com/groups/guitarexchange");


        System.out.println(group);


/*

        Elements descriptions = doc.select("div[class*='_5pbx']");
        Elements titles = doc.select("div[class='_l53']");
        Elements prices = doc.select("div[class='_l57']");
        Elements locations = doc.select("div[class='_l58']");
        Elements urls = doc.select("div[id*='feed_subtitle']");


        System.out.println(descriptions);
        System.out.println(titles);

        for (int i=0; i<10; i++) {
            System.out.println(descriptions.get(i).text());
            System.out.println(titles.get(i).text());
            System.out.println(prices.get(i).text());
            System.out.println(locations.get(i).text());
            System.out.println(urls.get(i).text());
            System.out.println("\n");
        }
        */

    }

}
