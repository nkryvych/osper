package ch.epfl.osper.metadata.wikireader.wikimodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by kryvych on 10/12/14.
 */
@XStreamAlias("siteinfo")
public class SiteInfo {
    private String sitename;
    private String base;
    private String generator;

    @XStreamAlias("case")
    private String caseLetter;
    private Namespaces namespaces;

    public SiteInfo(String sitename, String base, String generator, String caseLetter, Namespaces namespaces) {
        this.sitename = sitename;
        this.base = base;
        this.generator = generator;
        this.caseLetter = caseLetter;
        this.namespaces = namespaces;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public Namespaces getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Namespaces namespaces) {
        this.namespaces = namespaces;
    }

    public String getCase() {
        return caseLetter;
    }

    public void setCase(String caseLetter) {
        this.caseLetter = caseLetter;
    }
}
