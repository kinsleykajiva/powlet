package shepherdsithole.com.powlet.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import shepherdsithole.com.powlet.DBAccess.Preffs;
import shepherdsithole.com.powlet.R;
import shepherdsithole.com.powlet.mMessages.NifftyDialogs;
import shepherdsithole.com.powlet.mMessages.SeeTastyToast;

import static shepherdsithole.com.powlet.DBAccess.CRUD.getLastCreditBalance;
import static shepherdsithole.com.powlet.DBAccess.CRUD.saveRedeeemCredit;
import static shepherdsithole.com.powlet.mNetWorks.GenNetRequests.requestCreditToken;

public class CreaditTokens extends AppCompatActivity {
private TextView balance;
private EditText redeem,pin;
private Button btnConfirm;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = CreaditTokens.this;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_creadit_tokens );
        initObjects ();
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intiViews();
        setViewsData();
        clicks();

    }

    private void clicks () {
        btnConfirm.setOnClickListener ( ec->{
            String redeem_ = redeem.getText ().toString ().trim ();
            String pin_ = pin.getText ().toString ().trim ();

            if ( pin_.isEmpty () ) {
                pin.setError ( "Pin Number cant be empty." );
                return;
            }
            if ( redeem_.isEmpty () ) {
                redeem.setError ( "Reed Token cant be empty." );
                return;
            }
            String user = preffs.getUSER_NAME ();
            String mobile = preffs.getMobileNumber ();
            showProgressDialog(true);
            new AsyncTask<Void,Void,String[]> (){

                @Override
                protected String[] doInBackground (Void... voids) {
                    return requestCreditToken(
                            user ,pin_ , redeem_ ,mobile
                    );
                }
                @Override
                protected void onPostExecute (String[] s) {
                    super.onPostExecute ( s );
                    showProgressDialog ( false );
                    if ( s[0].isEmpty () ) {
                        Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
                     //   new NifftyDialogs ( context ).messageOkError ( "Connection Failed", "try again" );
                    }
                    if (  s[0].equals ( "done" ) ) {
                        Toast.makeText(context, "Transaction Done", Toast.LENGTH_LONG).show();
                         // Sting setreedemed , String setcreditsBalance
                        saveRedeeemCredit(redeem_ , s[1] );
                        balance.setText("Balance : $"+s[1] );
                   //     new NifftyDialogs ( context ).messageOk ( "Transaction Done" );

                        redeem.setText ( "" );
                        pin.setText ( "" );
                    }
                    if (  s[0].equals ( "failed" ) ) {
                        Toast.makeText(context, "Transaction Failed , Try again", Toast.LENGTH_LONG).show();                     
                    }
                    if ( s[0].equals("failed")) {
                        Toast.makeText(context, "Transaction Failed , Try again", Toast.LENGTH_LONG).show();
                    }
                    if ( s[0].equals("broke")) {
                        Toast.makeText(context, "Please recharge  your credit", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Please recharge  your credit", Toast.LENGTH_LONG).show();
                    }
                    if(s[0].equals("user-unfound")){
                        Toast.makeText(context, "Wrong pin", Toast.LENGTH_LONG).show();
                    }

                }
            }.execute (  );
        } );
    }

    private void setViewsData () {
        String ba = getLastCreditBalance ();
        if(ba.equals ( "0" )){
            balance.setText("Initial Bal : $500" );
        }else{
            balance.setText("Balance : $"+ba );
        }

    }
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
    }
    private void showProgressDialog (final boolean isToShow) {
        if ( Build.VERSION.SDK_INT < 26 ) {
            if ( isToShow ) {
                if ( ! progressDialog.isShowing () ) {
                    progressDialog.setMessage ( "Processing ...Please wait." );
                    progressDialog.setCancelable ( false );
                    progressDialog.show ();
                }
            } else {
                if ( progressDialog.isShowing () ) {
                    progressDialog.dismiss ();
                }
            }
        } else {
                /* this is Android Oreo and above*/
            if ( isToShow ) {
                topProgressBar.setVisibility ( View.VISIBLE );
                downProgressBar.setVisibility ( View.VISIBLE );
                getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
            } else {
                topProgressBar.setVisibility ( View.GONE );
                downProgressBar.setVisibility ( View.GONE );
                getWindow ().clearFlags ( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE );
            }
        }
    }
    private void intiViews () {
        balance = findViewById ( R.id.balance );
        pin = findViewById ( R.id.pin );
        redeem = findViewById ( R.id.redeem );
        btnConfirm = findViewById ( R.id.btnConfirm );
        topProgressBar = findViewById ( R.id.top_progressBar );
        downProgressBar = findViewById ( R.id.down_progressBar );
        if ( Build.VERSION.SDK_INT < 26 ) {
            progressDialog = new ProgressDialog ( this );
        } else {
            topProgressBar.setIndeterminate ( true );
            downProgressBar.setIndeterminate ( true );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
