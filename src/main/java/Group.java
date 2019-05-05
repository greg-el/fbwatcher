import java.util.List;

public class Group {
    private String url;
    private String name;
    private List<String> keywords;

    public Group(String url, String name, List<String> keywords) {
        this.url = url;
        this.name = name;
        this.keywords = keywords;
    }

    public Group() {
        this.url = null;
        this.name = null;
        this.keywords = null;
    }

    @Override
    public String toString() {
        return(url + "\n" + name);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords){
        this.keywords = keywords;
    }


}
