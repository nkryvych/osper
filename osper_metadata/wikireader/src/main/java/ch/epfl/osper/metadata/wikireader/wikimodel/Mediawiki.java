package ch.epfl.osper.metadata.wikireader.wikimodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by kryvych on 10/12/14.
 */
@XStreamAlias("mediawiki")
public class Mediawiki {

    private SiteInfo siteinfo;

    @XStreamImplicit(itemFieldName="page")
    private List<WikiPage> page;

    public Mediawiki(SiteInfo siteinfo, List<WikiPage> page) {
        this.siteinfo = siteinfo;
        this.page = page;
    }

    public SiteInfo getSiteinfo() {
        return siteinfo;
    }

    public void setSiteinfo(SiteInfo siteinfo) {
        this.siteinfo = siteinfo;
    }

    public List<WikiPage> getPage() {
        return page;
    }


    public void setPage(List<WikiPage> page) {
        this.page = page;
    }
}
