package utils;

import android.os.Handler;

public class Constant {
    public static final String SHARED_PREF_NAME = "JSHARED_PREFS";
    public static  String BASE_URL ="https://api.gatecontrolx.com:9000/api/v1/";
    public static  int CheckOut_Call = 1;
    public static  int CheckOut_Success = 2;
    public static  int CheckOut_Failed = 3;
    public static String GuardName = "";

    public static String Date_Format = "yyyy-MM-dd HH:mm:ss";

    public static final long REFRESH_INTERVAL = 2 * 60 * 60 * 1000; // 2 hours in milliseconds


    public static long Exp_Time = 0;

    //"http://125.63.88.147:7401/api/  local acxiom
//    http://125.63.88.147:2047/api/ 16-02-24

//    http://13.71.25.15:85/api/ 20-03-24
//    http://13.71.25.15:85/api/GetData/LoginAPI


    public static String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";

    public static int totalapicount = 0;
    public static int apicountreto = 0;

//    when a signature is required isSignature will be true
//    public static final boolean isSignature = true;

}
