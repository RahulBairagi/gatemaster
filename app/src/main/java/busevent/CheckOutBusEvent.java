package busevent;

public class CheckOutBusEvent {

    String ReqID, Msg;
    int Type, Pos;


    public CheckOutBusEvent(String reqID, int type, int pos ,String msg) {
        ReqID = reqID;
        Type = type;
        Msg = msg;
        Pos = pos;
    }

    public int getPos() {
        return Pos;
    }

    public void setPos(int pos) {
        Pos = pos;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        this.Type = type;
    }

    public String getReqID() {
        return ReqID;
    }

    public void setReqID(String reqID) {
        ReqID = reqID;
    }
}
