package ch.epfl.osper.metadata.wikireader;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by kryvych on 10/12/14.
 */
@XStreamAlias("contributor")
public class ContributorXML {
    private String id;
    private String username;

    public ContributorXML(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ContributorXML{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
