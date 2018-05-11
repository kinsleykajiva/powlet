package shepherdsithole.com.powlet.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import shepherdsithole.com.powlet.DBAccess.CRUD;
import shepherdsithole.com.powlet.DBAccess.Preffs;
import shepherdsithole.com.powlet.R;
import shepherdsithole.com.powlet.mMessages.NifftyDialogs;
import shepherdsithole.com.powlet.mMessages.SeeTastyToast;

import static shepherdsithole.com.powlet.mNetWorks.GenNetRequests.registerUser;

public class Register extends AppCompatActivity {
private EditText name,surname,password,meterNumber,phoneNumber;
private Button btnRegister ,btnBacktoLohin;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = Register.this;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
        initObjects ();
        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews ();

        clicks ();

    }
    private void clicks () {
        btnRegister.setOnClickListener ( rv->{
            String name_ = name.getText ().toString ().trim ();
            String surname_ = surname.getText ().toString ().trim ();
            String password_ = password.getText ().toString ().trim ();
            String meterNumber_ = meterNumber.getText ().toString ().trim ();
            String phoneNumber_ = phoneNumber.getText ().toString ().trim ();


            if ( name_.isEmpty () ) {
                name.setError ( "Name cant be empty." );
                return;
            }
            if ( surname_.isEmpty () ) {
                surname.setError ( "Surname cant be empty." );
                return;
            }
            if ( password_.isEmpty () ) {
                password.setError ( "Pin cant be empty." );
                return;
            }
            if ( meterNumber_.isEmpty () ) {
                meterNumber.setError ( "meter Number cant be empty." );
                return;
            }
            if ( phoneNumber_.isEmpty () ) {
                phoneNumber .setError ( "Phone Number cant be empty." );
                return;
            }
            showProgressDialog ( true);
            new AsyncTask<Void,Void,String> (){

                @Override
                protected String doInBackground (Void... voids) {
                    return registerUser(
                            name_ ,surname_, password_ , meterNumber_ , phoneNumber_
                    );
                }
                @Override
                protected void onPostExecute (String s) {
                    super.onPostExecute ( s );
                    showProgressDialog ( false );
                    if ( s.isEmpty () ) {
                        Toast.makeText ( context, "Connection Failed", Toast.LENGTH_LONG ).show ();
                      //  new NifftyDialogs ( context ).messageOkError ( "Connection Failed", "try again" );
                    }
                    if ( s.equals ( "done" ) ) {


                        CRUD.saveUser (
                                name_ ,surname_, password_ , meterNumber_ , phoneNumber_
                        );
                        preffs.setUSER_NAME ( name_ );
                        preffs.setLogStatus ( true );
                        preffs.setMobileNumber(phoneNumber_);
                        startActivity ( new Intent ( context , MainActivity.class ) );
                        finish ();
                    }
                    if ( s.equals ( "found" ) ) {
                        Toast.makeText ( context, "User exists already", Toast.LENGTH_LONG ).show ();
                     //   new NifftyDialogs ( context ).messageOkError ( "Response", "Transaction Failed" );
                    }
                    if (s.equals("failed")) {
                        Toast.makeText(context, "Failed to save,Try again", Toast.LENGTH_LONG).show();
                        // new NifftyDialogs ( context ).messageOkError ( "Response", "Transaction
                        // Failed" );
                    }

                }

            }.execute (  );

        } );
        btnBacktoLohin.setOnClickListener ( rv->{

        } );
    }
    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
    }
    private void initViews () {
        name = findViewById ( R.id.name );
        surname = findViewById ( R.id.surname );
        password = findViewById ( R.id.password );
        meterNumber = findViewById ( R.id.meterNumber );
        phoneNumber = findViewById ( R.id.phoneNumber );
        btnRegister = findViewById ( R.id.btnRegister );
        btnBacktoLohin = findViewById ( R.id.btnBacktoLohin );
        topProgressBar = findViewById ( R.id.top_progressBar );
        downProgressBar = findViewById ( R.id.down_progressBar );
        if ( Build.VERSION.SDK_INT < 26 ) {
            progressDialog = new ProgressDialog ( this );
        } else {
            topProgressBar.setIndeterminate ( true );
            downProgressBar.setIndeterminate ( true );
        }
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
