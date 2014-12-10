package ch.epfl.osper.metadata.model;

import java.util.Date;

/**
 * Created by kryvych on 01/12/14.
 */
public class DeploymentPeriod {

    private Date from;
    private Date to;

    public DeploymentPeriod(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
