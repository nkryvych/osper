package ch.epfl.osper.metadata.wikireader.wikimodel;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.InputStream;
import java.util.List;

/**
 * Created by kryvych on 10/12/14.
 */

public class MediaWikiXMLReader {

    public List<WikiPage> parse(String xmlString) {
        XStream xstream = new XStream(new StaxDriver());

        xstream.processAnnotations(Mediawiki.class);
        xstream.processAnnotations(SiteInfo.class);
        xstream.processAnnotations(WikiPage.class);
        xstream.processAnnotations(Namespaces.class);
        xstream.processAnnotations(ContributorXML.class);
        xstream.processAnnotations(RevisionXML.class);

        return ((Mediawiki) xstream.fromXML(xmlString)).getPage();

    }

    public List<WikiPage> parse(InputStream xmlString) {
        XStream xstream = new XStream(new StaxDriver());

        xstream.processAnnotations(Mediawiki.class);
        xstream.processAnnotations(SiteInfo.class);
        xstream.processAnnotations(WikiPage.class);
        xstream.processAnnotations(Namespaces.class);
        xstream.processAnnotations(ContributorXML.class);
        xstream.processAnnotations(RevisionXML.class);

        return ((Mediawiki) xstream.fromXML(xmlString)).getPage();

    }
    public String write() {
        XStream xstream = new XStream(new StaxDriver());

        xstream.processAnnotations(Mediawiki.class);
        xstream.processAnnotations(SiteInfo.class);
        xstream.processAnnotations(WikiPage.class);
        xstream.processAnnotations(Namespaces.class);
        xstream.processAnnotations(ContributorXML.class);
        xstream.processAnnotations(RevisionXML.class);

        SiteInfo siteInfo = new SiteInfo("name", "base", "gene", "case", new Namespaces(Lists.newArrayList("ns1", "ns2")));
        WikiPage page1 = new WikiPage("title", "id",
                new RevisionXML("id", "timestamp", new ContributorXML("id", "name"), "TEXT"));
        WikiPage page2 = new WikiPage("title1", "id1",
                new RevisionXML("id", "timestamp", new ContributorXML("id", "name"), "TEXT"));
        Mediawiki mediawiki = new Mediawiki(siteInfo, Lists.newArrayList(page1, page2));
        return xstream.toXML(mediawiki);

    }
}
