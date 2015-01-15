package ch.epfl.osper.metadata.gsnimport.xmlmodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by kryvych on 15/01/15.
 */
@XStreamAlias("gsn")
public class GSN {

    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;

    @XStreamAlias("author")
    @XStreamAsAttribute
    private String author;

    public GSN(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
