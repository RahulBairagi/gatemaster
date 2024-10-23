package db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gatemaster.ui.HomeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import busevent.BusEventDefault;
import datamodel.CustomerInvoiceDataModel;
import datamodel.InvoiceDataModel;
import model.ActiveEntriesResponse;
import model.GateEntry;
import model.ResponseData;

public class DatabaseConnection extends SQLiteOpenHelper {

    SQLiteDatabase sq;

    private static final int DATABASE_VERSION = 1;
    private static DatabaseConnection mInstance = null;

    private static final String DATABASE_NAME = "gatemaster.db";


    private String TAG = "Database";


    public static DatabaseConnection getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseConnection(ctx);
        }
        return mInstance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        db.disableWriteAheadLogging();
    }


    private static final String CREATE_TABLE_USERDETAIL= " create table if not exists UserDetail(userid text,name text,email text,mobile text);";

    private static final String CREATE_TABLE_INVOICEDETAIL=" create table if not exists InvoiceDetail(userid text,terminal text, customerAcct text, transactionId text, customerName text, signatureNotRequired text);";

    private static final String CREATE_TABLE_CUSTOMERINVOICEDETAIL=" create table if not exists CustomerInvoiceDetail(terminal text, customerAccount text, transactionId text, customerName text, signature text, invoiceURL text);";

    private static final String CREATE_TABLE_SIGNATUREDETAIL=" create table if not exists SignatureDetail(invoiceID text,custAcct text, signature blob, id text, post text,status text);";

    private static final String CREATE_TABLE_GATE_ENTRIES = "CREATE TABLE IF NOT EXISTS GateEntries (vehicle_registration_number TEXT, visitor_name TEXT, visitor_mobile TEXT, visitor_address TEXT, purpose TEXT, entry_time TEXT, created_type TEXT, modified_type TEXT, created_at TEXT, updated_at TEXT);";




    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERDETAIL);
        db.execSQL(CREATE_TABLE_INVOICEDETAIL);
        db.execSQL(CREATE_TABLE_CUSTOMERINVOICEDETAIL);
        db.execSQL(CREATE_TABLE_SIGNATUREDETAIL);
        db.execSQL(CREATE_TABLE_GATE_ENTRIES);


          }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_USERDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_INVOICEDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_CUSTOMERINVOICEDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_SIGNATUREDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_GATE_ENTRIES);


        onCreate(db);
    }

    public void insertUserDetails(String userid,String name, String email,String mobile ){
        sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put("userid",userid);
            contentValues.put("name",userid);
            contentValues.put("email",userid);
            contentValues.put("mobile",userid);
            long i= sq.insert("UserDetail",null,contentValues);
            Log.d(TAG, "UserDetail: ex =============>" + i);
        }catch (Exception e){
            Log.d(TAG, "InsertUserDetail: ex =============>" + e.getMessage());
        }
    }

    public Cursor getUserDetails() {
        sq = this.getReadableDatabase();
        Cursor res = sq.rawQuery("select  userid from UserDetail", null);
        return res;
    }

    public void deleteTable(String tlbName) {
        sq = this.getWritableDatabase();
        sq.execSQL("DELETE from '" + tlbName + "'");
    }

    public void insertInvoiceDetails(InvoiceDataModel invoiceDataModel){
        try {
            sq = this.getWritableDatabase();
            if (getInvoiceDetails()!=null) {
                ContentValues contentValues = new ContentValues();
                for (int i=0; i<invoiceDataModel.getTerminalsList().size();i++) {
                    String userid =invoiceDataModel.getTerminalsList().get(i).getUserid();
                    String terminal =  invoiceDataModel.getTerminalsList().get(i).getTerminal();

                    for (int j = 0; j < invoiceDataModel.getTerminalsList().get(i).getOrderLine().size(); j++) {
                        contentValues.put("userid",userid);
                        contentValues.put("terminal",terminal);
                        contentValues.put("customerAcct", invoiceDataModel.getTerminalsList().get(i).getOrderLine().get(j).getCustaccount());
                        contentValues.put("transactionId", invoiceDataModel.getTerminalsList().get(i).getOrderLine().get(j).getTransactionid());
                        contentValues.put("customerName", invoiceDataModel.getTerminalsList().get(i).getOrderLine().get(j).getCustname());
                        contentValues.put("signatureNotRequired", invoiceDataModel.getTerminalsList().get(i).getOrderLine().get(j).getSignaturenotrequired());
                        long k = sq.insert("InvoiceDetail", null, contentValues);
                        Log.d(TAG, "insertInvoiceDetails: insert =============>" + k);
                    }
                }
//                EventBus.getDefault().post(new BusEventDefault());
            }
        }catch (Exception e){
            Log.d(TAG, "InsertInvoiceDetail: ex =============>" + e.getMessage());
        }
    }

    public Cursor getInvoiceDetails(){
        sq = this.getReadableDatabase();
        Cursor res = sq.rawQuery("select  customerName,transactionId,customerAcct, signatureNotRequired from InvoiceDetail", null);
        return res;
    }

    public void insertCustomerInvoiceDetails(CustomerInvoiceDataModel customerInvoiceDataModel){
        sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put("terminal",customerInvoiceDataModel.getTerminal());
            contentValues.put("customerAccount",customerInvoiceDataModel.getCustaccount());
            contentValues.put("transactionId",customerInvoiceDataModel.getTransactionid());
            contentValues.put("customerName",customerInvoiceDataModel.getCustname());
            contentValues.put("signature",customerInvoiceDataModel.getSignature());
            contentValues.put("invoiceURL",customerInvoiceDataModel.getInvoiceURL());
            long i= sq.insert("CustomerInvoiceDetail",null,contentValues);
            Log.d(TAG, "value: ex =============>" + i);
        }catch (Exception e){
            Log.d(TAG, "InsertCustomerInvoiceDetail: ex =============>" + e.getMessage());
        }
    }
    public Cursor getCustomerInvoiceUrlDetails(){
        sq = this.getReadableDatabase();
        Cursor res = sq.rawQuery("select invoiceURL  from CustomerInvoiceDetail", null);
        return res;
    }
    public void insertSignatureDetailsGet(String invoiceID,String custAcct,String status, String id,String post,String signBaseStr){
        sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            contentValues.put("invoiceID",invoiceID);
            contentValues.put("custAcct",custAcct);
            contentValues.put("signature",signBaseStr);
            contentValues.put("id",id);
            contentValues.put("post",post);
            contentValues.put("status",status);
            long i = sq.insert("SignatureDetail",null,contentValues);
            Log.d(TAG, "value: ex =============>" + i);
        }catch (Exception e){
            Log.d(TAG,"InsertSignatureDetail: ex =============>"+e.getMessage());

        }
    }

    public void updateCustomerSign(byte[] byteArraySign,String invoiceID,String custAcct) {
        sq = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("signature", byteArraySign);
        contentValues.put("invoiceID",invoiceID);
        contentValues.put("custAcct",custAcct);
        contentValues.put("post","0");
//        sq.update("SignatureDetail", contentValues, "id=?", new String[]{HomeActivity.SIGNATUREID});
    }
   public Cursor getSignatureDetails(){
        sq = this.getReadableDatabase();
        Cursor res = sq.rawQuery("select invoiceID from SignatureDetail",null);
        return res;
   }

    public Cursor getCustomerSignatureDetail() {
        sq = this.getReadableDatabase();
        Cursor res = sq.rawQuery(" select * from SignatureDetail where post=0", null);
        return res;

    }

    public void updatePost(String post) {
        sq = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("post", post);
//        sq.update("SignatureDetail", contentValues, "id= ?", new String[]{HomeActivity.SIGNATUREID});
    }

    public void deleteBlankSign() {
        sq = this.getWritableDatabase();
        sq.execSQL("DELETE from SignatureDetail where  signature='' ");
    }
    public void deletePost(){
        sq = this.getWritableDatabase();
        sq.execSQL("DELETE from SignatureDetail where post='1'");
    }


    //Edited by Aayush -
    public void save_checkin(){

    }


    public void insertGateEntries(ActiveEntriesResponse data) {
        try {
            sq = this.getWritableDatabase();
            if (data != null && data.getResponseData() != null) {
                ContentValues contentValues = new ContentValues();
                for (ResponseData entry : data.getResponseData()) {
                    contentValues.clear();

                    contentValues.put("vehicle_registration_number", entry.getVehicleRegistrationNumber());
                    contentValues.put("visitor_name", entry.getVisitorName());
                    contentValues.put("visitor_mobile", entry.getVisitorMobile());
                    contentValues.put("visitor_address", entry.getVisitorAddress());
                    contentValues.put("purpose", entry.getPurpose());
                    contentValues.put("entry_time", entry.getEntryTime());
                    contentValues.put("created_type", entry.getCreatedType());
                    contentValues.put("modified_type", entry.getModifiedType());
                    contentValues.put("created_at", entry.getCreatedAt());
                    contentValues.put("updated_at", entry.getUpdatedAt());

                    long result = sq.insert("GateEntries", null, contentValues);
                    Log.d(TAG, "insertGateEntries: insert =============>" + result);
                }
//                EventBus.getDefault().post(new BusEventDefault()); // Notify listeners
            }
        } catch (Exception e) {
            Log.d(TAG, "insertGateEntries: ex =============>" + e.getMessage());
        } finally {
            if (sq != null) {
                sq.close();
            }
        }
    }

    public List<GateEntry> getGateEntries() {
        sq = this.getReadableDatabase();

        List<GateEntry> gateEntries = new ArrayList<>();

        // Query to fetch all records
        Cursor cursor = sq.rawQuery("SELECT vehicle_registration_number, visitor_name, visitor_mobile, visitor_address, purpose, entry_time, created_type, modified_type, created_at, updated_at FROM GateEntries", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String vehicleRegistrationNumber = cursor.getString(cursor.getColumnIndex("vehicle_registration_number"));
                @SuppressLint("Range") String visitorName = cursor.getString(cursor.getColumnIndex("visitor_name"));
                @SuppressLint("Range") String visitorMobile = cursor.getString(cursor.getColumnIndex("visitor_mobile"));
                @SuppressLint("Range") String visitorAddress = cursor.getString(cursor.getColumnIndex("visitor_address"));
                @SuppressLint("Range") String purpose = cursor.getString(cursor.getColumnIndex("purpose"));
                @SuppressLint("Range") String entryTime = cursor.getString(cursor.getColumnIndex("entry_time"));
                @SuppressLint("Range") String createdType = cursor.getString(cursor.getColumnIndex("created_type"));
                @SuppressLint("Range") String modifiedType = cursor.getString(cursor.getColumnIndex("modified_type"));
                @SuppressLint("Range") String createdAt = cursor.getString(cursor.getColumnIndex("created_at"));
                @SuppressLint("Range") String updatedAt = cursor.getString(cursor.getColumnIndex("updated_at"));

                // Add new GateEntry object to the list
                gateEntries.add(new GateEntry(
                        vehicleRegistrationNumber,
                        visitorName,
                        visitorMobile,
                        visitorAddress,
                        purpose,
                        entryTime,
                        createdType,
                        modifiedType,
                        createdAt,
                        updatedAt
                ));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return gateEntries;
    }

    public Cursor getVisitorCursorByMobile(String mobileNumber) {
        sq = this.getReadableDatabase();
        return sq.rawQuery("SELECT visitor_name, visitor_address,vehicle_registration_number FROM GateEntries WHERE visitor_mobile = ?", new String[]{mobileNumber});
    }


}
