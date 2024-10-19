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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface GateApi {

    @POST("guard/login")
    Call<LoginDataModel> userValidate(@Body UserDetailRequest jsonData);

    @POST("GetData/GetInvoiceId")
    Call<InvoiceDataModel> getInvoiceId(@Body InvoiceDetailRequest jsonData);

    @POST("GetData/GetInvoiceDetails")
    Call<CustomerInvoiceDataModel> getInvoiceDetails(@Body CustomerInvoiceRequest jsonData);

    @POST("Updatedata/UpdateSignatureInvoiceDetails")
    Call<SignatureDataModel> postSignatureDetails(@Body SignatureDetailRequest jsonData);

    @POST("GetData/GetVersion")
    Call<VersionDataModel> getVersionDetails(@Body VersionDetailRequest jsonData);

}