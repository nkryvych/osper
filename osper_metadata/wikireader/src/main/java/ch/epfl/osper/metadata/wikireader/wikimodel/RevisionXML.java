package ch.epfl.osper.metadata.wikireader.wikimodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by kryvych on 10/12/14.
 */
@XStreamAlias("revision")
public class RevisionXML {
    private String id;
    private String timestamp;
    private ContributorXML contributor;
    private String text;
    private String comment;
    private String minor;


    public RevisionXML(String id, String timestamp, ContributorXML contributor, String text) {
        this.id = id;
        this.timestamp = timestamp;
        this.contributor = contributor;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ContributorXML getContributor() {
        return contributor;
    }

    public void setContributor(ContributorXML contributor) {
        this.contributor = contributor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    @Override
    public String toString() {
        return "RevisionXML{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", contributor=" + contributor +
                ", text='" + text + '\'' +
                '}';
    }
}
