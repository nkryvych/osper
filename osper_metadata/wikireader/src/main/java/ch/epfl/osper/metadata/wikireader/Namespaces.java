package ch.epfl.osper.metadata.wikireader;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by kryvych on 10/12/14.
 */
public @XStreamAlias("namespaces")
class Namespaces {
    @XStreamImplicit(itemFieldName="namespace")
    List<String> namespace;

    public Namespaces(List<String> namespace) {
        this.namespace = namespace;
    }

    public List<String> getNamespace() {
        return namespace;
    }

    public void setNamespace(List<String> namespace) {
        this.namespace = namespace;
    }
}