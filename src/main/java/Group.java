import java.util.List;

public class Group {
    private String url;
    private String name;
    private List<String> keywords;

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
