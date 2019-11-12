package ku.piii2019.gui2;

public class MediaItemColumnInfo {

    private String heading;
    private int minWidth;
    private String property;

    public String getHeading() {
        return heading;
    }

    public MediaItemColumnInfo setHeading(String heading) {
        this.heading = heading;
        return this;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public MediaItemColumnInfo setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public MediaItemColumnInfo setProperty(String property) {
        this.property = property;
        return this;
    }
}