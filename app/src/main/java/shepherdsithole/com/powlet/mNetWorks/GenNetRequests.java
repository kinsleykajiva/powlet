package shepherdsithole.com.powlet.mNetWorks;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 */

public class GenNetRequests {
 /// http://www.rdsol.co.zw/shephard/
    public static final String MAIN_URL = "http://www.rdsol.co.zw/shephard/";

    public static String registerUser (
            String name , String Surname , String password  ,
            String metterNuymber
    ,String PhoneNumber) {

        OkHttpClient client = new OkHttpClient ();
        String Serverresponse = "";

        RequestBody body = new FormBody.Builder ()
                .add ( "poST_name", name )
                .add ( "poST_Surname", Surname)
                .add ( "poST_password", password)
                .add ( "poST_metterNuymber", metterNuymber)
                .add ( "poST_PhoneNumber", PhoneNumber)
                .build ();
        Request request = new Request.Builder ()
                .url ( MAIN_URL + "registerUser.php" )
                .post ( body )
                .build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                JSONObject json = (new JSONObject ( response.body ().string () ));
                Serverresponse = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }

        return Serverresponse;
    }


    public static String []requestsMobileNumber (final String user, String TransNumber , String amount_ ,String pin) {

        OkHttpClient client = new OkHttpClient ();
        String [] Serverresponse = {"",""};


        RequestBody body = new FormBody.Builder ()
                .add ( "poST_user", user )
                .add ( "poST_TransNumber", TransNumber )
                .add ( "poST_amount_", amount_)
                .add ( "poST_pin", pin)
                .build ();
        Request request = new Request.Builder ()
                .url ( MAIN_URL + "mobilenumber.php" )
                .post ( body )
                .build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                String as=response.body ().string ();

                JSONObject json = (new JSONObject (as ));

                Serverresponse [0]= json.getString ( "message" );
                if(json.getString("message").equals ("done")){
                Serverresponse [1]= json.getString ( "token" );
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }

        return Serverresponse;
    }

    public static String requestBankCard (
            final String user, final String cardNumber, final String amount, final String civ
           ) {

        OkHttpClient client = new OkHttpClient ();
        String Serverresponse = "";

        RequestBody body = new FormBody.Builder ()
                .add ( "poST_user", user )
                .add ( "poST_cardNumber", cardNumber )
                .add ( "poST_amount", amount )
                .add ( "poST_civ", civ )
               
                .build ();
        Request request = new Request.Builder ()
                .url ( MAIN_URL + "bankcard.php" )
                .post ( body )
                .build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                String sa=response.body ().string ();
               // Log.e ( "xxx", "requestBankCard: "+sa  );
                JSONObject json = (new JSONObject ( sa ));
                Serverresponse = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }

        return Serverresponse;
    }

    public static String[] requestCreditToken (
            final String user,
            final String pin, final String redeem, final String mobilNumber) {

        OkHttpClient client = new OkHttpClient ();
        String [] Serverresponse = {"",""};

        RequestBody body = new FormBody.Builder ()
                .add ( "poST_user", user )
                .add ( "poST_pin", pin )
                .add ( "poST_redeem", redeem )
                .add ( "poST_number", mobilNumber )
                .build ();
        Request request = new Request.Builder ()
                .url ( MAIN_URL + "creditRedeemToken.php" )
                .post ( body )
                .build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                String sa=response.body ().string ();
               //  Log.e ( "xxx", "requestCreditToken: "+sa  );
                JSONObject json = (new JSONObject ( sa ));
                if(json.getString("message").equals("done")){
                    Serverresponse[1] = json.getString("balance");
                }
                Serverresponse[0] = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }

        return Serverresponse;
    }
    
    public static String requestFaultMessage(final String messsge, final String user, final String mobilNumber) {

        OkHttpClient client = new OkHttpClient();
        String Serverresponse = "";

        RequestBody body = new FormBody.Builder()
        .add("poST_user", user)
        .add("poST_report_messsge", messsge)
                .add("poST_number", mobilNumber).build();
        Request request = new Request.Builder().url(MAIN_URL + "faultreport.php").post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String sa=response.body ().string ();
               // Log.e ( "xxx", "requestFaultMessage: "+sa  );
                JSONObject json = (new JSONObject(sa));
                Serverresponse = json.getString("message");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Serverresponse;
    }

    public static String requestSendMessage (
            final String messsge, final String user, final String mobilNumber) {

        OkHttpClient client = new OkHttpClient ();
        String Serverresponse = "";

        RequestBody body = new FormBody.Builder ()
                .add ( "poST_user", user )
                .add ( "poST_messsge", messsge )
                .add ( "poST_number", mobilNumber )
                .build ();
        Request request = new Request.Builder ()
                .url ( MAIN_URL + "message.php" )
                .post ( body )
                .build ();
        try {
            Response response = client.newCall ( request ).execute ();
            if ( response.isSuccessful () ) {
                JSONObject json = (new JSONObject ( response.body ().string () ));
                Serverresponse = json.getString ( "message" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace ();
        }

        return Serverresponse;
    }
}
