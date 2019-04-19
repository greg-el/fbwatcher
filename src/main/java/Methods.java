import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Methods {

    public String getHTMLSource(String url, WebDriver driver) {
        driver.get(url);
        return(driver.getPageSource());
    }

    public WebDriver login(String email, String password, WebDriver driver) {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
        driver.findElement(By.xpath("//input[starts-with(@id, 'u_0_')][@value='Log In']")).click();
        return driver;
    }

    public Map getMemberGroups(WebDriver driver) {
        //New method instance
        Methods methods = new Methods();

        //Getting group page html
        String rawHTML = methods.getHTMLSource("https://www.facebook.com/groups/?category=membership", driver);
        Document doc = Jsoup.parse(rawHTML);

        //Regex init
        String urlString = "(href=\")(.*?)(?=\")";
        String nameString = "(>)(.*?)(?=<)";
        Pattern urlPattern = Pattern.compile(urlString);
        Pattern namePattern = Pattern.compile(nameString);

        //return dict
        Map<String, String> strGroups = new HashMap<String, String>();

        //selecting and adding to strGroups
        Elements groups = doc.select("div[class='_266w']");

        for (int i=0; i<groups.size(); i++) {
            String groupString = groups.get(i).toString();
            Matcher urlM = urlPattern.matcher(groupString);
            Matcher nameM = namePattern.matcher(groupString);
            if (urlM.find() && nameM.find()) {
                strGroups.put(nameM.group(2), urlM.group(2));
            }
        }

        return strGroups;
    }

    public List getGroupPosts(WebDriver driver, String url) {
        // groups have all kind of weird post content, make sure it works for all of em
        Methods methods = new Methods();
        String rawHTML = methods.getHTMLSource(url, driver);
        Document doc = Jsoup.parse(rawHTML);

        String patternString = "(href=\")(.*?)(?=\")";
        Pattern p = Pattern.compile(patternString);
        List groupPosts = new ArrayList();

        Elements descriptions = doc.select("div[class*='_5pbx']");
        Elements titles = doc.select("div[class='_l53']");
        Elements prices = doc.select("div[class='_l57']");
        Elements locations = doc.select("div[class='_l58']");
        Elements dates = doc.select("div[id*='feed_subtitle']");

        Elements urls = doc.select("span[class='fsm fwn fcg']");

        System.out.println(doc);
        int i;

        System.out.println(urls.size());
        for (i = 0; i<urls.size(); i++) {
            Matcher m = p.matcher(urls.get(i).toString());
            System.out.println(titles.get(i));
            if (m.find()) {
                List<String> tempGroupList = Arrays.asList(m.group(2),
                        descriptions.get(i).toString(),
                        titles.get(i).toString(),
                        prices.get(i).toString(),
                        locations.get(i).toString(),
                        dates.get(i).toString());

                tempGroupList.add(String.valueOf(groupPosts));

            }

        }
        return groupPosts;

    }

}
