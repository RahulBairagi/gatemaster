package busevent;

public class InvoiceBusEvent {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public InvoiceBusEvent(String status){
        this.status = status;

    }
}
