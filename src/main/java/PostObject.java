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

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getUrl() {
        return url;
    }
}