import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.Assert.*;

public class FbScraperTest {

    @Test
    public void testGetGroupPostsReturns() throws Exception {
        FbScraper fbScraper = new FbScraper();
        WebDriver driver = fbScraper.createDriver();

        String html = IOUtils.toString(this.getClass().getResourceAsStream("post_test.html"), "UTF-8");
        File temp = File.createTempFile("test_html_temp", ".html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        bw.write(html);
        bw.close();
        driver.get("file:///" + temp.getAbsolutePath());

        List<Post> posts = fbScraper.getGroupPosts(driver);

        for (Post post : posts) {
            assertEquals(post.getTitle(), "Title_test");
            assertEquals(post.getDescription(), "Description_test");
            assertEquals(post.getLocation(), "Location_test");
            assertEquals(post.getPrice(), "Price_test");
            assertEquals(post.getDatetime().toString(), "1974-03-29");
            assertEquals(post.getUrl(), "file:///tmp/Url_test");
        }

        driver.close();

    }


    @Test
    public void testGetLoggedInStatus() throws Exception {
        FbScraper fbScraper = new FbScraper();
        WebDriver driver = fbScraper.createDriver();
        String htmlIn = IOUtils.toString(this.getClass().getResourceAsStream("logged_in.html"), "UTF-8");
        File tempIn = File.createTempFile("logged_in_temp", ".html");
        BufferedWriter bwIn = new BufferedWriter(new FileWriter(tempIn));
        bwIn.write(htmlIn);
        bwIn.close();
        driver.get("file:///" + tempIn.getAbsolutePath());
        assertTrue("Checking logged_in_html", fbScraper.getLoggedInStatus(driver));

        String htmlOut = IOUtils.toString(this.getClass().getResourceAsStream("logged_out.html"), "UTF-8");
        File tempOut = File.createTempFile("logged_out_temp", ".html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempOut));
        bw.write(htmlOut);
        bw.close();
        driver.get("file:///" + tempOut.getAbsolutePath());
        assertFalse("Checking logged_out_html", fbScraper.getLoggedInStatus(driver));
        driver.close();
    }
}