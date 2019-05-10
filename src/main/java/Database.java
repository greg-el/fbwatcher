import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    void addKeywordToGroupDatabase(String groupName, String keyword) {
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT id FROM groups WHERE name=?");
             PreparedStatement pstmtInsert = connect.prepareStatement("INSERT INTO keywords (keyword, group_id) VALUES (?, ?)");
             Statement stmt = connect.createStatement())
        {
            int id = 0;
            stmt.executeUpdate("USE data;");
            pstmtSelect.setString(1, groupName);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }

            pstmtInsert.setString(1, keyword);
            pstmtInsert.setInt(2, id);
            pstmtInsert.executeUpdate();
        } catch (Exception e) {
            System.out.println("addKeyWordToGroupDatabase" + e);
        }
    }

    void removeKeywordFromGroupDatabase(String groupName, String keyword) {
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT id FROM groups WHERE name=?");
             PreparedStatement pstmtDelete = connect.prepareStatement("DELETE FROM keywords WHERE keyword=? AND group_id=?");
             Statement stmt = connect.createStatement())
        {
            int id = 0;
            stmt.executeUpdate("USE data;");
            pstmtSelect.setString(1, groupName);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }            pstmtDelete.setString(1, keyword);
            pstmtDelete.setInt(2, id);
            pstmtDelete.executeUpdate();
        } catch (Exception e) {System.out.println("removeKeywordFromGroupDatabase" + e); }
    }

    List<Group> getGroupsFromDatabase() {
        List<Group> groups = new ArrayList<>();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM groups;");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Group group = new Group(rs.getString("name"), rs.getString("url"), getGroupKeywordsFromName(rs.getString("name")));
                    groups.add(group);
                }
            }
        } catch (Exception e) {System.out.println("getGroupsFromDatabase" + e);}
        return groups;
    }

    List<String> getGroupKeywordsFromName(String name) {
        List<String> groups = new ArrayList<>();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM groups INNER JOIN keywords ON groups.id = keywords.group_id WHERE keywords.group_id=?;");
             Statement stmt = connect.createStatement())
        {

            stmt.executeUpdate("USE data;");
            pstmt.setString(1, getGroupIdFromName(name));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    groups.add(rs.getString("keyword"));
                }
            }
        } catch (Exception e) {System.out.println("getGroupKeywordsFromName" + e);}
        return groups;
    }

    List<String> getGroupListFromDatabase() {
        List<String> groupNames = new ArrayList<>();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT name FROM groups;");
             Statement stmt = connect.createStatement())

        {
            stmt.executeUpdate("USE data;");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("test0");
                    groupNames.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {System.out.println("getGroupListFromDatabase" + e);}

        return groupNames;
    }

    String getGroupUrlFromName(String name) {
        String url = null;
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT url FROM groups WHERE name=?;");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    url = rs.getString("url");
                }
            }
        } catch (Exception e) {System.out.println("getGroupUrlFromName: " + e);}
        return url;
    }

    Group getGroupDataFromName(String name) {
        Group group = new Group();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT url FROM groups WHERE name=?;");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    group.setName(name);
                    group.setUrl(rs.getString("url"));
                    group.setKeywords(getGroupKeywordsFromName(name));
                }
            }
        } catch (Exception e) {
            System.out.println("getGroupDataFromName: " + e);
        }
        return group;
    }

    private String getGroupIdFromName(String name) {
        String id = null;
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM groups WHERE name=?");
             Statement stmt = connect.createStatement())

        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    id = rs.getString("id");
                }
            }

        } catch (Exception e) {System.out.println("getGroupIdFromName: " + e);}
        return id;
    }

    boolean isPostInDatabase(Post post) {
        String postExist = null;
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM posts WHERE url=? LIMIT 1");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, post.getUrl());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    postExist = rs.getString("url");
                }
            }
            if (postExist != null) {
                System.out.println("Post already in database");
                return true;

            }

        } catch (Exception e) {System.out.println("isPostInDatabase " + e); }
        return false;
    }

    void addPostToDatabase(Post post) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmt = connect.prepareStatement("INSERT INTO posts " +
                     "(description, title, price, location, datetime, url) VALUE" +
                     "(?, ?, ?, ?, ?, ?)"))
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, post.getDescription());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getPrice());
            pstmt.setString(4, post.getLocation());
            pstmt.setString(5, post.getDatetime());
            pstmt.setString(6, post.getUrl());
            pstmt.executeUpdate();
            System.out.println("Adding post to database");
        } catch (Exception e) {
            System.out.println("addPostToDatabase" + e);
        }

    }

    void addGroupsToDatabase(List<Group> groups) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmt = connect.prepareStatement("INSERT INTO groups " +
                     "(name, url) VALUE" +
                     "(?, ?)"))
        {
            stmt.executeUpdate("USE data;");
            for (Group group : groups) {
                pstmt.setString(1, group.getName());
                pstmt.setString(2, group.getUrl());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("addGroupsToDatabase" + e);
        }
    }

    void addJoinedGroups(List<Group> groups) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT * FROM groups WHERE url = ?");
             PreparedStatement pstmtDelete = connect.prepareStatement("INSERT INTO groups " +
                     "(name, url) VALUE" +
                     "(?, ?)"))
        {
            stmt.executeUpdate("USE data;");
            for (Group group : groups) {
                pstmtSelect.setString(1, group.getUrl());
                try (ResultSet rs = pstmtSelect.executeQuery()) {
                    while(rs.next()) {
                        if (rs.getString("url") == null) {
                            pstmtDelete.setString(1, group.getName());
                            pstmtDelete.setString(2, group.getUrl());
                            pstmtDelete.executeUpdate();
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("addJoinedGroups" + e);
        }
    }

    void removeLeftGroups(List<Group> groups) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT url FROM groups");
             PreparedStatement pstmtDelete = connect.prepareStatement("DELETE FROM groups WHERE url=?"))
        {
            stmt.executeUpdate("USE data;");
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    boolean match = false;
                    for (Group group : groups) {
                        if (group.getUrl().equals(rs.getString("url"))) {
                            match = true;
                        }
                    }
                    if (!match) {
                        pstmtDelete.setString(1, rs.getString("url"));
                        pstmtDelete.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {System.out.println("removeLeftGroups" + e);}
    }
}
