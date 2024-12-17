package busevent;

public class PanicBusEvent {

    public String getStrEvent() {
        return strEvent;
    }

    public void setStrEvent(String strEvent) {
        this.strEvent = strEvent;
    }

    private String strEvent;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;


    public PanicBusEvent(String strEvent,String status) {
        this.strEvent = strEvent;
        this.status=status;

    }





}
