public class Group {
    private String url;
    private String name;

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
}
