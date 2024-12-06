package busevent;

public  class BusEventDefault {

    String message;
    Boolean isSuccess;
    String error;

    public BusEventDefault(String message, Boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public BusEventDefault(String message, Boolean isSuccess, String error) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
