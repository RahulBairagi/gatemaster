package db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.Retail3xpress.GateControlX.ui.HomeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import busevent.BusEventDefault;
import datamodel.CustomerInvoiceDataModel;
import datamodel.InvoiceDataModel;
import model.ActiveEntriesResponse;
import model.GateEntry;
import model.GateResponseData;
import model.GetGatesResponse;
import model.ResponseData;

public class DatabaseConnection extends SQLiteOpenHelper {

    SQLiteDatabase sq;

    private static final int DATABASE_VERSION = 1;
    private static DatabaseConnection mInstance = null;

    private static final String DATABASE_NAME = "GateMaster.db";


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

    private static final String CREATE_TABLE_GATE_ENTRIES = "CREATE TABLE IF NOT EXISTS GateEntries (vehicle_registration_number TEXT, visitor_name TEXT, visitor_mobile TEXT, visitor_address TEXT, purpose TEXT, entry_time TEXT, created_type TEXT, modified_type TEXT, created_at TEXT, updated_at TEXT, GateReqID TEXT);";

    private static final String CREATE_TABLE_Gates = "CREATE TABLE IF NOT EXISTS Gates (id INTEGER PRIMARY KEY, gate_id TEXT, location TEXT, entry_type TEXT, status INTEGER, client_id INTEGER, created_type TEXT, modified_type TEXT, created_at TEXT, updated_at TEXT);";




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
        db.execSQL(CREATE_TABLE_Gates);


          }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_USERDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_INVOICEDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_CUSTOMERINVOICEDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_SIGNATUREDETAIL);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_GATE_ENTRIES);
        db.execSQL("drop table if exists"+" "+CREATE_TABLE_Gates);
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
                    contentValues.put("GateReqID", entry.getGateEntriesRequestId());

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
        Cursor cursor = sq.rawQuery("SELECT vehicle_registration_number, visitor_name, visitor_mobile, visitor_address, purpose, entry_time, created_type, modified_type, created_at, updated_at, GateReqID FROM GateEntries", null);

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
                @SuppressLint("Range") String GateReqID = cursor.getString(cursor.getColumnIndex("GateReqID"));

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
                        updatedAt,
                        GateReqID
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
    public void deleteGateEntry(String ReqID) {
        sq = this.getReadableDatabase();
        sq.rawQuery("Delete FROM GateEntries WHERE GateReqID = ?", new String[]{ReqID});
    }

    public void clearAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all rows from each table
        db.execSQL("DELETE FROM UserDetail");
        db.execSQL("DELETE FROM InvoiceDetail");
        db.execSQL("DELETE FROM CustomerInvoiceDetail");
        db.execSQL("DELETE FROM SignatureDetail");
        db.execSQL("DELETE FROM GateEntries");

        Log.d(TAG, "All tables cleared");
    }


    public void insertGate(GetGatesResponse data) {
        try {
            sq = this.getWritableDatabase();
            if (data != null && data.getResponseData() != null) {
                ContentValues contentValues = new ContentValues();
                for (GateResponseData entry : data.getResponseData()) {
                    contentValues.clear();

                    contentValues.put("id", entry.getId());
                    contentValues.put("gate_id", entry.getGateId());
                    contentValues.put("location", entry.getLocation());
                    contentValues.put("entry_type", entry.getEntryType());
                    contentValues.put("status", entry.getStatus());
                    contentValues.put("client_id", entry.getClientId());
                    contentValues.put("created_type", entry.getCreatedType());
                    contentValues.put("modified_type", entry.getModifiedType());
                    contentValues.put("created_at", entry.getCreatedAt());
                    contentValues.put("updated_at", entry.getUpdatedAt());

                    long result = sq.insert("Gates", null, contentValues);
                    Log.d(TAG, "Gates: Insert result =============> " + result);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Gates: Exception =============> " + e.getMessage());
        } finally {
            if (sq != null) {
                sq.close();
            }
        }
    }


    public Map<String, String> getGateLocationMap() {
        Map<String, String> locationGateIdMap = new HashMap<>();
        SQLiteDatabase sq = null;
        Cursor cursor = null;
        try {
            sq = this.getReadableDatabase();
            String query = "SELECT location, gate_id FROM Gates";
            cursor = sq.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex("location"));
                    @SuppressLint("Range") String gateId = cursor.getString(cursor.getColumnIndex("gate_id"));
                    locationGateIdMap.put(location, gateId);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "getGateLocationMap: Exception =============> " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sq != null) {
                sq.close();
            }
        }
        return locationGateIdMap;
    }



}
