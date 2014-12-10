package ch.epfl.osper.metadata.wikireader;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by kryvych on 10/12/14.
 */
@XStreamAlias("page")
public class WikiPage {
    private String title;
    private String id;
    private RevisionXML revision;

    public WikiPage(String title, String id, RevisionXML revision) {
        this.title = title;
        this.id = id;
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RevisionXML getRevision() {
        return revision;
    }

    public void setRevision(RevisionXML revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return "MeasurementLocationXML{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", revision=" + revision +
                '}';
    }
}
