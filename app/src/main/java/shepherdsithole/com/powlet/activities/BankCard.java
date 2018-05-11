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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import shepherdsithole.com.powlet.DBAccess.CRUD;
import shepherdsithole.com.powlet.DBAccess.Preffs;
import shepherdsithole.com.powlet.R;
import shepherdsithole.com.powlet.cwidgets.CustomSpinnerAdapter;
import shepherdsithole.com.powlet.mMessages.NifftyDialogs;
import shepherdsithole.com.powlet.mMessages.SeeTastyToast;

import static shepherdsithole.com.powlet.mNetWorks.GenNetRequests.requestBankCard;

public class BankCard extends AppCompatActivity {
    private Spinner bank;
    private EditText cardNumber, civ, amount;
    private Button btnConfirm;
    private ProgressBar topProgressBar, downProgressBar;
    private ProgressDialog progressDialog;
    private SeeTastyToast toasty;
    private Preffs preffs;
    private Context context = BankCard.this;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_bank_card );
        initObjects ();
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        initViews ();
        setData ();
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
        btnConfirm.setOnClickListener ( ev -> {
            String cardNumber_ = cardNumber.getText ().toString ().trim ();
            String civ_ = civ.getText ().toString ().trim ();
            String amount_ = amount.getText ().toString ().trim ();

            if ( cardNumber_.isEmpty () ) {
                cardNumber.setError ( "Card Number cant be empty." );
                return;
            }
            if ( civ_.isEmpty () ) {
                civ.setError ( "Civ Number cant be empty." );
                return;
            }
            if ( amount_.isEmpty () ) {
                amount.setError ( "Amount cant be empty." );
                return;
            }

            LayoutInflater inflater = getLayoutInflater ();
            View alertLayout = inflater.inflate ( R.layout.dialog_layout, null );
            ((TextView) alertLayout.findViewById ( R.id.textView1 )).setText ( "Are you sure you want to proceed ?" );
            AlertDialog.Builder alert = new AlertDialog.Builder ( context );
            alert.setTitle ( "Confirmation Dialog" );
            alert.setView ( alertLayout );
            alert.setCancelable ( false );

            alert.setPositiveButton ( "Yes", (dialogInterface, i) -> {


                showProgressDialog ( true );
                String user = preffs.getUSER_NAME ();
                String mobile = preffs.getMobileNumber ();
                new AsyncTask < Void, Void, String > () {

                    @Override
                    protected String doInBackground (Void... voids) {
                        return requestBankCard ( user,
                                cardNumber_, amount_
                                , civ_ );
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
                            
                            Toast.makeText ( context, "Transaction Done", Toast.LENGTH_LONG ).show ();
                            CRUD.saveBankCard (cardNumber_ , civ_ , amount_ , mobile);
                           /// new NifftyDialogs ( context ).messageOk ( "Transaction Done" );

                            cardNumber.setText ( "" );
                            civ.setText ( "" );
                            amount.setText ( "" );
                        }
                        if ( s.equals ( "failed" ) ) {
                            Toast.makeText ( context, "Transaction Failed", Toast.LENGTH_LONG ).show ();
                           // new NifftyDialogs ( context ).messageOkError ( "Response", "Transaction Failed" );
                        }
                    }
                }.execute ();
            } ).setNeutralButton ( "Cancel", (dialog, which) -> dialog.dismiss () );
            AlertDialog dialog = alert.create ();
            dialog.show ();
        } );
    }

    private void setData () {
        ArrayList < String > catagory = new ArrayList <> ( Arrays.asList ( "CBZ", "ZB", "Steward" ) );
        CustomSpinnerAdapter catagorySpinnerAdapter = new CustomSpinnerAdapter ( this, catagory );
        bank.setAdapter ( catagorySpinnerAdapter );
        bank.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view, int position, long id) {

                String select = parent.getItemAtPosition ( position ).toString ();


            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent) {

            }
        } );
    }

    protected void initObjects () {
        preffs = new Preffs ( context );
        toasty = new SeeTastyToast ( context );
    }

    private void initViews () {
        bank = findViewById ( R.id.bank );
        cardNumber = findViewById ( R.id.cardNumber );
        civ = findViewById ( R.id.civ );
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
