package busevent;


public class VisiteeDetails {

    String name,address;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    Boolean isSuccess;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public VisiteeDetails(String name, String address, Boolean isSuccess) {
        this.name = name;
        this.address = address;
        this.isSuccess = isSuccess;
    }
}
