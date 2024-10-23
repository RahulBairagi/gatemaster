package retrofit;


import datamodel.CustomerInvoiceDataModel;
import datamodel.CustomerInvoiceRequest;
import datamodel.InvoiceDataModel;
import datamodel.InvoiceDetailRequest;
import datamodel.LoginDataModel;
import datamodel.SignatureDataModel;
import datamodel.SignatureDetailRequest;
import datamodel.UserDetailRequest;
import datamodel.VersionDataModel;
import datamodel.VersionDetailRequest;
import model.ActiveEntriesResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface GateApi {

    @POST("guard/login")
    Call<LoginDataModel> userValidate(@Body UserDetailRequest jsonData);

    @GET("guard/getactiveentries")
    Call<ActiveEntriesResponse> getActiveEntries(@Header("Authorization") String authToken);

}