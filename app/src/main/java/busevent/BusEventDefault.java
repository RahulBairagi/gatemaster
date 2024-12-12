package busevent;

public  class BusEventDefault {

    String event;
    Boolean isSuccess;
    String message;

    public BusEventDefault(String event, Boolean isSuccess) {
        this.event = event;
        this.isSuccess = isSuccess;
    }

    public BusEventDefault(String event, Boolean isSuccess, String message) {
        this.event = event;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
