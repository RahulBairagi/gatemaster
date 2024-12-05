package retrofit;


import datamodel.CustomerInvoiceDataModel;
import datamodel.CustomerInvoiceRequest;
import datamodel.InvoiceDataModel;
import datamodel.InvoiceDetailRequest;
import datamodel.LoginDataModel;
import datamodel.PostCheckIn;
import datamodel.PostCheckOut;
import datamodel.ResponseCheckIn;
import datamodel.ResponseCheckOut;
import datamodel.ResponseVisiteeDetails;
import datamodel.SignatureDataModel;
import datamodel.SignatureDetailRequest;
import datamodel.TokenRefreshModel;
import datamodel.UserDetailRequest;
import datamodel.VersionDataModel;
import datamodel.VersionDetailRequest;
import model.ActiveEntriesResponse;
import model.GetGatesResponse;
import model.LogoutResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface GateApi {

    @POST("guard/login")
    Call<LoginDataModel> userValidate(@Body UserDetailRequest jsonData);

    @POST("guard/checkin")
    Call<ResponseCheckIn> postcheckin(@Header("Authorization") String authToken, @Body PostCheckIn jsonData);

    @GET("guard/getactiveentries")
    Call<ActiveEntriesResponse> getActiveEntries(@Header("Authorization") String authToken);


    @GET("guard/gates")
    Call<GetGatesResponse> getAllGates(@Header("Authorization") String authToken);


    @GET("guard/getvisitordetails/{mobileNumber}")
    Call<ResponseVisiteeDetails> getVisiteeDetails(
            @Header("Authorization") String authToken,
            @Path("mobileNumber") String mobileNumber);

    @POST("guard/checkout")
    Call<ResponseCheckOut> postcheckout(@Header("Authorization") String authToken, @Body PostCheckOut jsonData);

    @POST("guard/logout")
    Call<LogoutResponse> logout(@Header("Authorization") String authToken);

    @POST("guard/refresh")
    Call<TokenRefreshModel> refreshToken(@Header("Authorization") String authToken);



}