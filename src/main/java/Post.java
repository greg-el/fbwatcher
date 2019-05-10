import java.util.Date;
import java.util.List;

class Post {
    private String description= null;
    private String title = null;
    private String price= null;
    private String location= null;
    private java.sql.Date datetime= null;
    private String url= null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public java.sql.Date getDatetime() {
        return datetime;
    }

    public void setDatetime(java.sql.Date datetime) {
        this.datetime = datetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return (description + "\n" + title + "\n" + price + "\n" + location + "\n" + datetime + "\n" + url + "\n\n");
    }

    boolean containsKeyword(Post post, List<String> keywords) {
        for (String keyword : keywords) {
            if (post.getDescription() != null && post.getDescription().contains(keyword) || post.getTitle() != null && post.getTitle().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

}