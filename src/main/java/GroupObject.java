public class GroupObject {
    public String url = null;
    public String name = null;

    @Override
    public String toString() {
        return(url + "|*|" + name);
    }
}
