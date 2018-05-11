package shepherdsithole.com.powlet.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import shepherdsithole.com.powlet.DBAccess.CRUD;
import shepherdsithole.com.powlet.DBAccess.Preffs;
import shepherdsithole.com.powlet.R;
import shepherdsithole.com.powlet.mMessages.NifftyDialogs;
import shepherdsithole.com.powlet.mMessages.SeeTastyToast;

import static shepherdsithole.com.powlet.mNetWorks.GenNetRequests.requestsMobileNumber;
import static shepherdsithole.com.powlet.mServices.MyFirebaseMessagingService.sendNotification;

public class MobileNumber extends AppCompatActivity {
    private EditText ecocash, telecash, netcash, amount;
    private Button btnConfirm;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = MobileNumber.this;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_mobile_number );
        initObjects ();
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        initViews ();
        clicks ();

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

    private void clicks () {
        btnConfirm.setOnClickListener ( v -> {
            String ecocash_ = ecocash.getText ().toString ().trim ();
            String telecash_ = telecash.getText ().toString ().trim ();
            String netcash_ = netcash.getText ().toString ().trim ();
            String amount_ = amount.getText ().toString ().trim ();

            if ( ecocash_.isEmpty () && telecash_.isEmpty () && netcash_.isEmpty ()  ) {
                String ms = "Use one of these !";
                ecocash.setError ( ms );
                telecash.setError ( ms );
                netcash.setError ( ms );

                return;
            }
            if (  amount_.isEmpty () ) {
                String ms = "Put Amount !!";

                amount.setError ( ms );
                return;
            }
            String TransNumber ="" ;
            if( !ecocash_.isEmpty () &&telecash_.isEmpty () && netcash_.isEmpty ()  ){
                TransNumber = ecocash_;
            }
            else if(ecocash_.isEmpty () && !telecash_.isEmpty () && netcash_.isEmpty ()  ){
                TransNumber  =telecash_;
            }
            else if(ecocash_.isEmpty () && telecash_.isEmpty () && !netcash_.isEmpty ()  ){
                TransNumber  =netcash_;
            }


            showDialogUSSD( TransNumber,amount_ );



        } );
    }

    private void showDialogUSSD (String TransNumber , String amount_) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_feedback, null);
        final EditText message=(EditText) alertLayout.findViewById(R.id.editfeedback);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Pin Request.");alert.setView(alertLayout);alert.setCancelable(false);

        alert.setPositiveButton("Send",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {

                if(!message.getText().toString().trim().isEmpty()) {
                    dialogInterface.dismiss();
                    showProgressDialog ( true );
                    String userNumber = preffs.getMobileNumber ();
                    String pin = message.getText ().toString ().trim();
                    new AsyncTask < Void, Void, String[] > () {

                        @Override
                        protected String[] doInBackground (Void... voids) {
                            return requestsMobileNumber ( userNumber, TransNumber ,  amount_,pin );
                        }

                        @Override
                        protected void onPostExecute (String []s) {
                            super.onPostExecute ( s );
                            showProgressDialog ( false );
                            if ( s[0].isEmpty () ) {
                                Toast.makeText ( context, "Connection Failed", Toast.LENGTH_LONG ).show ();
                               // new NifftyDialogs ( context ).messageOkError ( "Connection Failed", "try again" );
                            }
                            if ( s[0].equals ( "done" ) ) {
                                Toast.makeText ( context, "Transaction Done", Toast.LENGTH_LONG ).show ();
                              //  new NifftyDialogs ( context ).messageOk ( "Transaction Done","Please for  a token" );
                                CRUD.saveTransactionMobileNumber(userNumber ,userNumber, TransNumber ,  amount_ , s[1]);
                                ecocash.setText ( "" );
                                telecash.setText ( "" );
                                netcash.setText ( "" );
                                amount.setText ( "" );
                                sendNotification(context, "Token",s[1] , CRUD.getTranctionID() +"" );
                                Toast.makeText ( context, "Waiting for token", Toast.LENGTH_SHORT ).show ();
                            }
                            if ( s[0].equals ( "unfound" ) ) {
                                Toast.makeText ( context, "Transaction Failed", Toast.LENGTH_LONG ).show ();
                              //  new NifftyDialogs ( context ).messageOkError ( "Response", "Transaction Failed" );
                            }
                            if ( s[0].equals ( "user-unfound" ) ) {
                                Toast.makeText ( context, "Wrong Pin", Toast.LENGTH_LONG ).show ();
                             //   new NifftyDialogs ( context ).messageOkError ( "Response", "Wrong Pin" );
                            }
                        }
                    }.execute ();
                }else{
                    Toast.makeText ( context, "No pin Entered !!", Toast.LENGTH_LONG ).show ();
                }
            }
        }).setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss() );AlertDialog dialog = alert.create();dialog.show();
       }

    private void initViews () {
        ecocash = findViewById ( R.id.ecocash );
        telecash = findViewById ( R.id.telecash );
        netcash = findViewById ( R.id.netcash );
        amount = findViewById ( R.id.amount );
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

    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        int id = item.getItemId ();

        if ( id == android.R.id.home ) {

            onBackPressed ();
            return true;
        }


        return super.onOptionsItemSelected ( item );
    }
}

