import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Methods {

    public Document login(String email, String password, String group) {
        Connection.Response req;
        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

            req = Jsoup.connect("https://m.facebook.com/login/async/?refsrc=https%3A%2F%2Fm.facebook.com%2F&lwv=101")
                    .userAgent(userAgent)
                    .method(Connection.Method.POST).data("email", email).data("pass", password)
                    .followRedirects(true)
                    .execute();


            Document d = Jsoup.connect(group)
                    .userAgent(userAgent)
                    .cookies(req.cookies())
                    .get();

            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
