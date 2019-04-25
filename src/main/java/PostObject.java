class PostObject {
    public String description = null;
    public String title = null;
    public String price = null;
    public String location = null;
    public String datetime = null;
    public String url = null;

    @Override
    public String toString() {
        return (description + title + price + location + datetime + url);
    }
}