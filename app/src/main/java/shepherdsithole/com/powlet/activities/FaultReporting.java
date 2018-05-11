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
import android.widget.Toast;

import shepherdsithole.com.powlet.DBAccess.Preffs;
import shepherdsithole.com.powlet.R;
import shepherdsithole.com.powlet.mMessages.NifftyDialogs;
import shepherdsithole.com.powlet.mMessages.SeeTastyToast;

import static shepherdsithole.com.powlet.mNetWorks.GenNetRequests.requestCreditToken;
import static shepherdsithole.com.powlet.mNetWorks.GenNetRequests.requestFaultMessage;

public class FaultReporting extends AppCompatActivity {
    private EditText message;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private Button btnConfirm;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = FaultReporting.this;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_fault_reporting );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
        message = findViewById (R.id.message);
        topProgressBar = findViewById ( R.id.top_progressBar );
        downProgressBar = findViewById ( R.id.down_progressBar );
        if ( Build.VERSION.SDK_INT < 26 ) {
            progressDialog = new ProgressDialog ( this );
        } else {
            topProgressBar.setIndeterminate ( true );
            downProgressBar.setIndeterminate ( true );
        }
        btnConfirm = findViewById ( R.id.btnConfirm );
        btnConfirm.setOnClickListener ( ex->{
            String message_ = message.getText ().toString ().trim ();

            if ( message_.isEmpty () ) {
                message.setError ( "Message cant be empty." );
                return;
            }
                String user = preffs.getUSER_NAME ();
                String mobile = preffs.getMobileNumber ();
                showProgressDialog(true);
                new AsyncTask<Void,Void,String> (){

                    @Override
                    protected String doInBackground (Void... voids) {
                        return requestFaultMessage(
                                message_ , user , mobile
                        );
                    }
                    @Override
                    protected void onPostExecute (String s) {
                        super.onPostExecute ( s );
                        showProgressDialog ( false );
                        if ( s.isEmpty () ) {
                            Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
                           // new NifftyDialogs ( context ).messageOkError ( "Connection Failed", "try again" );
                        }
                        if ( s.equals ( "done" ) ) {
                            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
                          ///  new NifftyDialogs ( context ).messageOk ( "Message Sent" );

                            message.setText ( "" );

                        }
                        if ( s.equals ( "unfound" ) ) {
                            Toast.makeText(context, "Message not Sent,Try again", Toast.LENGTH_LONG).show();
                         //   new NifftyDialogs ( context ).messageOkError ( "Response", "Message not Sent,Try again" );
                        }
                    }
                }.execute (  );

        } );
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
