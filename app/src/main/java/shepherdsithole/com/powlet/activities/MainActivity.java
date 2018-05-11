package shepherdsithole.com.powlet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;

import shepherdsithole.com.powlet.DBAccess.Preffs;
import shepherdsithole.com.powlet.R;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mobile_layout , bank_card , wallet ,electricity ,fault_report ;
private Preffs preffs;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        preffs = new Preffs ( this );
        if(!preffs.checkIfLoggedIn ()){
            startActivity ( new Intent ( this,Register.class ) );
            finish ();
        }


        mobile_layout = findViewById ( R.id.mobile_layout );
        bank_card = findViewById ( R.id.bank_card );
        wallet = findViewById ( R.id.wallet );
        electricity = findViewById ( R.id.electricity );
        fault_report = findViewById ( R.id.fault_report );

        mobile_layout.setOnClickListener ( ev->{
            startActivity ( new Intent ( this,MobileNumber.class ) );
        } );
        bank_card.setOnClickListener ( ev->{
            startActivity ( new Intent ( this,BankCard.class ) );
        } );
        wallet.setOnClickListener ( ev->{
            startActivity ( new Intent ( this,CreaditTokens.class ) );
        } );
        electricity.setOnClickListener ( ev->{
            startActivity ( new Intent ( this,PuchasisHistory.class ) );
        } );
        fault_report.setOnClickListener ( ev->{
            startActivity ( new Intent ( this,FaultReporting.class ) );
        } );

    }



}
