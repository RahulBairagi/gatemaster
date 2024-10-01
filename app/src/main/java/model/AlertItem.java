package model;

public class AlertItem {
    private String header;
    private String subheader;

    public AlertItem(String header, String subheader) {
        this.header = header;
        this.subheader = subheader;
    }

    public String getHeader() {
        return header;
    }

    public String getSubheader() {
        return subheader;
    }
}

